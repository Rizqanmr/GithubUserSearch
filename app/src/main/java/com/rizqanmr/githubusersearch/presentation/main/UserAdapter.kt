package com.rizqanmr.githubusersearch.presentation.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rizqanmr.githubusersearch.data.model.User
import com.rizqanmr.githubusersearch.databinding.ItemUserBinding
import com.rizqanmr.githubusersearch.utils.setCircleImageUrl

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var userListener: UserListener

    private val diffUtil = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun saveData(dataResponse: List<User>){
        asyncListDiffer.submitList(dataResponse)
    }

    fun setUserListener(userListener: UserListener) {
        this.userListener = userListener
    }

    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("PrivateResource")
        fun bindData(user: User?, listener: UserListener) {
            binding.let { itemUser ->
                itemUser.item = user
                with(itemUser) {
                    ivAvatar.setCircleImageUrl(
                        user?.avatarUrl,
                        com.google.android.material.R.drawable.mtrl_ic_error
                    )
                    itemLayout.setOnClickListener { listener.onItemClick(itemUser, user) }
                }
            }
        }
    }

    interface UserListener {
        fun onItemClick(itemUserBinding: ItemUserBinding, user: User?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = asyncListDiffer.currentList[position]
        if (data != null) {
            holder.bindData(data, userListener)
        }
    }
}