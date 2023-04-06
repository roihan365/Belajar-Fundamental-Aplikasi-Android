package com.example.submissionroihan2

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submissionroihan2.adapter.SectionsPagerAdapter
import com.example.submissionroihan2.api.DetailUserResponse
import com.example.submissionroihan2.database.FavoriteEntity
import com.example.submissionroihan2.databinding.ActivityDetailBinding
import com.example.submissionroihan2.viewModel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    private var detailUser = DetailUserResponse()
    private var buttonState: Boolean = false
    private var favoriteUser: FavoriteEntity? = null
    companion object {
        const val ID = "id"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val FOLLS = "folls"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.listDetail.observe(this, { detail ->
            detailUser = detail
            setDataDetail(detailUser)
            favoriteUser = detailUser.login?.let { FavoriteEntity(it, detailUser.avatarUrl) }

            detailViewModel.getFavoriteList().observe(this, { favoriteList ->
                if (favoriteList != null) {
                    for (data in favoriteList) {
                        if (detailUser.login == data.username) {
                            buttonState = true
                            binding.fabFavorite.imageTintList = ColorStateList.valueOf(Color.rgb(255, 50, 50))
                        }
                    }
                }
            })


        binding.fabFavorite.setOnClickListener {
            if (!buttonState) {
                buttonState = true
                detailUser.login?.let { it1 -> detailUser.avatarUrl?.let { it2 ->
                    detailViewModel.addToFavorite(it1,
                        it2
                    )
                } }
                binding.fabFavorite.imageTintList = ColorStateList.valueOf(Color.rgb(255,50,50))
            } else {
                buttonState = false
                detailUser.login?.let { it1 -> detailViewModel.remove(it1) }
                binding.fabFavorite.imageTintList = ColorStateList.valueOf(Color.rgb(156, 154, 154))
            }

        }
        })
        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        setTabLayoutView()
    }

    private fun setDataDetail(detail: DetailUserResponse) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(detail.avatarUrl)
                .circleCrop()
                .into(ivPhoto)
            tvNama.text = detail.name
            tvUsername.text = detail.login
            tvFollower.text = detail.followers.toString() + " Follower"
            tvFollowing.text = detail.following.toString() + " Following"
        }
    }

    private fun setTabLayoutView() {
        val userIntent = intent.getParcelableExtra<ItemsItem>(ID) as ItemsItem
        detailViewModel.getGithubUser(userIntent.login)

        val login = Bundle()
        login.putString(FOLLS, userIntent.login)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, login)
        sectionsPagerAdapter.username = userIntent.login
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}