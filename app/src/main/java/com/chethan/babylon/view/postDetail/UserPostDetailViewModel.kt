package com.chethan.babylon.view.postDetail


import androidx.lifecycle.*
import com.chethan.babylon.model.Comments
import com.chethan.babylon.model.PostWithComments
import com.chethan.babylon.model.Posts
import com.chethan.babylon.repository.UserPostRepository
import com.chethan.babylon.repository.Resource
import com.chethan.babylon.repository.UserCommentsRepository
import com.chethan.babylon.testing.OpenForTesting
import com.chethan.babylon.utils.AbsentLiveData
import java.util.*
import javax.inject.Inject

/**
 * Created by Chethan on 7/30/2019.
 *
 * User post detail has three different data to display on the screen, Post, User Comments, User
 * In order to make
 */
@OpenForTesting
class UserPostDetailViewModel @Inject constructor(
    userPostViewModel: UserPostRepository,
    userCommentsRepository: UserCommentsRepository
) : ViewModel() {


    private val postId = MutableLiveData<String>()
    val result = MediatorLiveData<Resource<PostWithComments>>()
    private lateinit var userComments: LiveData<Resource<List<Comments>>>
    private lateinit var userPosts: LiveData<Resource<Posts>>


    init {


        userPosts = Transformations
            .switchMap(postId) { post ->
                if (post.isNullOrBlank()) {
                    AbsentLiveData.create()
                } else {
                    userPostViewModel.getPosts(postId.value!!)
                }
            }

        userComments = Transformations
            .switchMap(postId) { search ->
                if (search.isNullOrBlank()) {
                    AbsentLiveData.create()
                } else {
                    userCommentsRepository.getUserComments(postId.value!!)
                }
            }

        result.addSource(userComments) { value ->
            result.value = combineLatestData(userComments.value?.data, userPosts.value?.data)
        }
        result.addSource(userPosts) { value ->
            result.value = combineLatestData(userComments.value?.data, userPosts.value?.data)
        }
    }


    private fun combineLatestData(
        comments: List<Comments>?,
        posts: Posts?
    ): Resource<PostWithComments> {

        // Don't send a success until we have both results
        if (comments == null || posts == null) {
            return Resource.loading(null)
        }

        return Resource.success(PostWithComments(post = posts, comments = comments))
    }

    fun setPostId(postId: String) {
        val input = postId.toLowerCase(Locale.getDefault()).trim()
        if (input == this.postId.value) {
            return
        }
        this.postId.value = input
    }
}


