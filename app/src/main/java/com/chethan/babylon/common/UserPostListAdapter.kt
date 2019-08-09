package com.chethan.babylon.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.chethan.babylon.AppExecutors
import com.chethan.babylon.R
import com.chethan.babylon.databinding.UserPostItemBinding
import com.chethan.babylon.model.Posts


class UserPostListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val repoClickCallback: ((Posts) -> Unit)?

) : DataBoundListAdapter<Posts, UserPostItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Posts>() {
        override fun areItemsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem.title == newItem.title
                    && oldItem.body == newItem.body
        }

        override fun areContentsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem.title == newItem.title
                    && oldItem.body == newItem.body
        }
    }
) {

    override fun createBinding(parent: ViewGroup): UserPostItemBinding {
        val binding = DataBindingUtil.inflate<UserPostItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.user_post_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.posts?.let {
                repoClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: UserPostItemBinding, item: Posts) {
        binding.posts = item
    }
}
