package com.chethan.babylon.common

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chethan.babylon.AppExecutors
import com.chethan.babylon.databinding.UserDetailFragmentHeaderBinding
import com.chethan.babylon.databinding.UserDetailsCommentsItemBinding
import com.chethan.babylon.model.Comments
import com.chethan.babylon.model.PostWithComments
import com.chethan.babylon.model.Posts

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class UserCommentsAdapter(
    appExecutors: AppExecutors
) : ListAdapter<DataItem, RecyclerView.ViewHolder>(
    AsyncDifferConfig.Builder(DiffCallback())
        .setBackgroundThreadExecutor(appExecutors.diskIO())
        .build()
) {


    class DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

    fun addHeaderAndSubmitList(postWithComments: PostWithComments) {

        val items = when (postWithComments) {
            null -> listOf(DataItem.HeaderItem(postWithComments.post))
            else -> listOf(DataItem.HeaderItem(postWithComments.post)) + postWithComments.comments.map {
                DataItem.CommentsItem(it)
            }
        }

        submitList(items)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CommentsHolder -> {
                val item = getItem(position) as DataItem.CommentsItem
                holder.bind(item.userComment)
            }

            is PostHeaderHolder -> {
                val item = getItem(position) as DataItem.HeaderItem
                holder.bind(item.userPost)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> PostHeaderHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> CommentsHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.HeaderItem -> ITEM_VIEW_TYPE_HEADER
            is DataItem.CommentsItem -> ITEM_VIEW_TYPE_ITEM
            else -> throw ClassCastException("Unknown getViewt=Type ${getItem(position)}")
        }
    }

    class PostHeaderHolder private constructor(val binding: UserDetailFragmentHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Posts) {
            binding.userPosts = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PostHeaderHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = UserDetailFragmentHeaderBinding.inflate(layoutInflater, parent, false)
                return PostHeaderHolder(binding)
            }
        }
    }


    class CommentsHolder private constructor(val binding: UserDetailsCommentsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comments) {
            binding.comments = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CommentsHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = UserDetailsCommentsItemBinding.inflate(layoutInflater, parent, false)
                return CommentsHolder(binding)
            }
        }
    }
}


sealed class DataItem {
    data class CommentsItem(val userComment: Comments) : DataItem() {
        override val id = userComment.id
    }

    data class HeaderItem(val userPost: Posts) : DataItem() {
        override val id = userPost.id
    }

    abstract val id: String
}



