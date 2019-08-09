package com.chethan.babylon.repository

import androidx.lifecycle.LiveData
import com.chethan.demoproject.utils.RateLimiter
import com.chethan.babylon.AppExecutors
import com.chethan.babylon.api.ApiSuccessResponse
import com.chethan.babylon.api.NetWorkApi
import com.chethan.babylon.db.CommentsDao
import com.chethan.babylon.model.Comments
import com.chethan.babylon.testing.OpenForTesting
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@OpenForTesting
class UserCommentsRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val commentsDao: CommentsDao,
    private val netWorkApi: NetWorkApi
) {

    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)
    /**
     * Expecting Master json response to deliver list of posts category
     */
    fun getUserComments(postId: String): LiveData<Resource<List<Comments>>> {
        return object : NetworkBoundResource<List<Comments>, List<Comments>>(appExecutors) {

            // inserting into data base
            override fun saveCallResult(item: List<Comments>) {
                commentsDao.insertComments(item)
            }

            override fun shouldFetch(data: List<Comments>?) = repoListRateLimit.shouldFetch("userComments.json")

            override fun loadFromDb(): LiveData<List<Comments>> {
                return commentsDao.loadComments(postId)
            }

            override fun createCall() = netWorkApi.getComments()


            override fun processResponse(response: ApiSuccessResponse<List<Comments>>)
                    : List<Comments> {
                val body = response.body
                return body
            }
        }.asLiveData()
    }


}
