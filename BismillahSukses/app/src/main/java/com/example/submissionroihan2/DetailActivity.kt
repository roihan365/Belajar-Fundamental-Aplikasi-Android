package com.example.submissionroihan2

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submissionroihan2.adapter.SectionsPagerAdapter
import com.example.submissionroihan2.api.DetailUserResponse
import com.example.submissionroihan2.databinding.ActivityDetailBinding
import com.faresa.githubsearchuser.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
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

        val photo = binding.ivPhoto
        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        detailViewModel.listDetail.observe(this, { detail ->
            setDataDetail(detail)
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