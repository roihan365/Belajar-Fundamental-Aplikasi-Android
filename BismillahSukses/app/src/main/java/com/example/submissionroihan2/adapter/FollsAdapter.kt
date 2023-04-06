package com.example.submissionroihan2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionroihan2.ItemsItem
import com.example.submissionroihan2.databinding.ItemRowGithubBinding

class FollsAdapter(private val listFollower: List<ItemsItem>) : RecyclerView.Adapter<FollsAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemRowGithubBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listFollower.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follower = listFollower[position]

        with(holder.binding) {
            Glide.with(holder.itemView.context)
                .load(follower.avatarUrl)
                .circleCrop()
                .into(ivPhoto)
            tvUser.text = follower.login
        }
    }

}