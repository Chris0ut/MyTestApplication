package com.example.myapplication.ui.users

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.model.User
import com.example.myapplication.databinding.ItemUserBinding
import com.example.myapplication.ui.details.DetailsFragment

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val users: MutableList<User> = mutableListOf()

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                itemView.isClickable = false
                Handler().postDelayed({ itemView.isClickable = true }, 500)

                val user = users[adapterPosition]
                val username = user.login
                val fragment = DetailsFragment.newInstance(username)
                val activity = itemView.context as AppCompatActivity

                activity.supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_in_left, R.anim.slide_out_right
                    )
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }


        fun bind(user: User) {
            binding.apply {
                Glide.with(itemView.context).load(user.avatarUrl).into(userAvatar)
                userName.text = user.login
                userId.text = "ID: ${user.id}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    fun setData(_users: List<User>) {
        users.addAll(_users)
        notifyDataSetChanged()
    }

    fun clearData() {
        users.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount() = users.size
}
