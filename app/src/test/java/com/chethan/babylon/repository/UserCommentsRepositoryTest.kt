package com.chethan.babylon.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.chethan.babylon.api.NetWorkApi
import com.chethan.babylon.db.AppDatabase
import com.chethan.babylon.db.CommentsDao
import com.chethan.babylon.model.Comments
import com.chethan.babylon.util.ApiUtil
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
class UserCommentsRepositoryTest {
    private lateinit var repository: UserCommentsRepository
    private val dao = mock(CommentsDao::class.java)
    private val service = mock(NetWorkApi::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    val item =
        TestUtil.getUserCommentItem()

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.commentsDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = UserCommentsRepository(InstantAppExecutors(), dao, service)
    }


    @Test
    fun loadUserCommentsListResponse() {
        val dbData = MutableLiveData<List<Comments>>()
        `when`(dao.loadComments("1")).thenReturn(dbData)

        val commentsArrayList = TestUtil.createUserCommentsArrayList(item)
        val call = ApiUtil.successCall(commentsArrayList)

        `when`(service.getComments()).thenReturn(call)

        val data = repository.getUserComments("1")
        verify(dao).loadComments("1")
        verifyNoMoreInteractions(service)

        val observer = mock<Observer<Resource<List<Comments>>>>()
        data.observeForever(observer)
        verifyNoMoreInteractions(service)
        verify(observer).onChanged(Resource.loading(null))

        val updatedDbData = MutableLiveData<List<Comments>>()
        `when`(dao.loadAllTheComments()).thenReturn(updatedDbData)

        dbData.postValue(null)
        verify(service).getComments()

        verify(dao).insertComments(commentsArrayList)

    }
}


