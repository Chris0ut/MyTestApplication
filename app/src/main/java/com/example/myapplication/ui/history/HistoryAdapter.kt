package com.example.myapplication.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.data.model.UserHistory
import com.example.myapplication.databinding.ItemUserHistoryBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.UserViewHolder>() {

    private var users: MutableList<UserHistory> = mutableListOf()

    inner class UserViewHolder(private val binding: ItemUserHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserHistory) {
            binding.apply {
                Glide.with(itemView.context).load(user.avatar).into(userAvatar)
                userName.text = user.name
                userEmail.text = "Email: ${user.email}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemUserHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    override fun getItemCount() = users.size

    fun updateUsers(newUsers: List<UserHistory>) {
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }
}