package com.chethan.babylon.repository

import androidx.lifecycle.LiveData
import com.chethan.demoproject.utils.RateLimiter
import com.chethan.babylon.AppExecutors
import com.chethan.babylon.api.ApiSuccessResponse
import com.chethan.babylon.api.NetWorkApi
import com.chethan.babylon.db.UsersDao
import com.chethan.babylon.model.Users
import com.chethan.babylon.testing.OpenForTesting
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@OpenForTesting
class UsersRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val usersDao: UsersDao,
    private val netWorkApi: NetWorkApi
) {

    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)
    private val categoryAll = "All"
    private val specialCharacter = "m"
    /**
     * Expecting Master json response to deliver list of posts category
     */
    fun getUserList(): LiveData<Resource<List<Users>>> {
        return object : NetworkBoundResource<List<Users>, List<Users>>(appExecutors) {

            // inserting into data base
            override fun saveCallResult(item: List<Users>) {
                usersDao.insertUsers(item)
            }

            override fun shouldFetch(data: List<Users>?) = repoListRateLimit.shouldFetch("users")

            override fun loadFromDb(): LiveData<List<Users>> {
                return usersDao.loadAllUsers()
            }

            override fun createCall() = netWorkApi.getUsers()


            override fun processResponse(response: ApiSuccessResponse<List<Users>>)
                    : List<Users> {
                val body = response.body
                return body
            }
        }.asLiveData()
    }


}
