package com.example.submissionroihan2

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionroihan2.adapter.UserAdapter
import com.example.submissionroihan2.database.FavoriteEntity
import com.example.submissionroihan2.databinding.ActivityFavoriteBinding
import com.example.submissionroihan2.viewModel.DetailViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel.getFavoriteList().observe(this) { users: List<FavoriteEntity> ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = it.avatarUrl?.let { it1 -> ItemsItem(login = it.username, avatarUrl = it1) }
                if (item != null) {
                    items.add(item)
                }
            }
            val userAdapter = UserAdapter(items, object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ItemsItem) {
                    showSelectedUser(data)
                }
            })
            binding.rvFavorite.adapter = userAdapter


//            binding.rvFavorite.adapter = UserAdapter(items as ArrayList<ItemsItem>)
            val layoutManager = LinearLayoutManager(this)
            binding.rvFavorite.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
            binding.rvFavorite.addItemDecoration(itemDecoration)
//            adapter.setOnItemCallback(object : UserAdapter.OnItemClickCallback {
//                override fun onItemClicked(data: ItemsItem) {
//                    showSelectedUser(data)
//                }
//
//            })
        }
    }

    private fun showSelectedUser(data: ItemsItem) {
        val moveWithParcelableIntent = Intent(this@FavoriteActivity, DetailActivity::class.java)
        moveWithParcelableIntent.putExtra(DetailActivity.ID, data)
        startActivity(moveWithParcelableIntent)
    }
}