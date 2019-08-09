package com.chethan.babylon.api

import androidx.lifecycle.LiveData
import com.chethan.babylon.model.Comments
import com.chethan.babylon.model.Posts
import com.chethan.babylon.model.Users
import retrofit2.http.GET

/**
 * Created by Chethan on 7/30/2019.
 * This interface contains the definition list of all the network endpoints used by the App.
 * Ref: Retrofit
 */
interface NetWorkApi {

    @GET("posts")
    fun getPost(): LiveData<ApiResponse<List<Posts>>>

    @GET("posts")
    fun getPost(id:String): LiveData<ApiResponse<Posts>>

    @GET("users")
    fun getUsers(): LiveData<ApiResponse<List<Users>>>

    @GET("comments")
    fun getComments(): LiveData<ApiResponse<List<Comments>>>


}