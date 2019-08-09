package com.chethan.babylon.view.userposts


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chethan.babylon.model.Posts
import com.chethan.babylon.repository.UserPostRepository
import com.chethan.babylon.repository.Resource
import com.chethan.babylon.testing.OpenForTesting
import javax.inject.Inject

/**
 * Created by Chethan on 7/30/2019.
 */
@OpenForTesting
class UserPostViewModel @Inject constructor(userPostRepository: UserPostRepository) : ViewModel() {
     var userPosts: LiveData<Resource<List<Posts>>> = userPostRepository.getAllPosts()
}


