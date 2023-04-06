package com.example.submissionroihan2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionroihan2.ItemsItem
import com.example.submissionroihan2.R

class UserAdapter(private val listUser: ArrayList<ItemsItem> = ArrayList<ItemsItem>(), private var onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgUser: ImageView = view.findViewById(R.id.iv_photo)
        val tvUser: TextView = view.findViewById(R.id.tv_user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(
        R.layout.item_row_github, parent, false))

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listUser[position]
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .circleCrop()
            .into(holder.imgUser)
        holder.tvUser.text = data.login
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }
}