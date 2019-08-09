package com.chethan.babylon.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.chethan.babylon.api.NetWorkApi
import com.chethan.babylon.db.AppDatabase
import com.chethan.babylon.db.PostsDao
import com.chethan.babylon.model.Posts
import com.chethan.babylon.util.ApiUtil.successCall
import com.chethan.babylon.util.InstantAppExecutors
import com.chethan.babylon.util.TestUtil
import com.chethan.babylon.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

/**
 * Created by Chethan on 7/30/2019.
 */

@RunWith(JUnit4::class)
class UserPostRepositoryTest {
    private lateinit var repository: UserPostRepository
    private val dao = mock(PostsDao::class.java)
    private val service = mock(NetWorkApi::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    val item =
        TestUtil.getUserPost()

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.postsDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = UserPostRepository(InstantAppExecutors(), dao, service)
    }


    @Test
    fun loadUserPostListResponse() {
        val dbData = MutableLiveData<List<Posts>>()
        `when`(dao.loadAllPost()).thenReturn(dbData)

        val usersArrayList = TestUtil.createUserPostArrayList(item)
        val call = successCall(usersArrayList)

        `when`(service.getPost()).thenReturn(call)

        val data = repository.getAllPosts()
        verify(dao).loadAllPost()
        verifyNoMoreInteractions(service)

        val observer = mock<Observer<Resource<List<Posts>>>>()
        data.observeForever(observer)
        verifyNoMoreInteractions(service)
        verify(observer).onChanged(Resource.loading(null))

        val updatedDbData = MutableLiveData<List<Posts>>()
        `when`(dao.loadAllPost()).thenReturn(updatedDbData)

        dbData.postValue(null)
        verify(service).getPost()

        verify(dao).insertPosts(usersArrayList)

    }


}