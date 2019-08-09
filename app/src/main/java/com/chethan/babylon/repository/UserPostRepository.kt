package com.chethan.babylon.repository

import androidx.lifecycle.LiveData
import com.chethan.demoproject.utils.RateLimiter
import com.chethan.babylon.AppExecutors
import com.chethan.babylon.api.ApiResponse
import com.chethan.babylon.api.ApiSuccessResponse
import com.chethan.babylon.api.NetWorkApi
import com.chethan.babylon.db.PostsDao
import com.chethan.babylon.model.Posts
import com.chethan.babylon.testing.OpenForTesting
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@OpenForTesting
class UserPostRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val postDao: PostsDao,
    private val netWorkApi: NetWorkApi
) {

    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)
    /**
     * Expecting Master json response to deliver list of posts category
     */
    fun getAllPosts(): LiveData<Resource<List<Posts>>> {
        return object : NetworkBoundResource<List<Posts>, List<Posts>>(appExecutors) {

            // inserting into data base
            override fun saveCallResult(item: List<Posts>) {
                postDao.insertPosts(item)
            }

            override fun shouldFetch(data: List<Posts>?) = repoListRateLimit.shouldFetch("master.json")

            override fun loadFromDb(): LiveData<List<Posts>> {
                return postDao.loadAllPost()
            }

            override fun createCall() = netWorkApi.getPost()


            override fun processResponse(response: ApiSuccessResponse<List<Posts>>)
                    : List<Posts> {
                val body = response.body
                return body
            }
        }.asLiveData()
    }


    fun getPosts(id:String): LiveData<Resource<Posts>> {
        return object : NetworkBoundResource<Posts, Posts>(appExecutors) {

            // inserting into data base
            override fun saveCallResult(item: Posts) {
                postDao.insertPost(item)
            }

            override fun shouldFetch(data: Posts?) = false

            override fun loadFromDb(): LiveData<Posts> {
                return postDao.loadPost(id)
            }

            override fun createCall() = netWorkApi.getPost(id)


            override fun processResponse(response: ApiSuccessResponse<Posts>)
                    : Posts {
                val body = response.body
                return body
            }
        }.asLiveData()
    }



}
