package com.chethan.babylon.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.chethan.babylon.api.NetWorkApi
import com.chethan.babylon.db.AppDatabase
import com.chethan.babylon.db.UsersDao
import com.chethan.babylon.model.Users
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
class UsersRepositoryTest {
    private lateinit var repository: UsersRepository
    private val dao = mock(UsersDao::class.java)
    private val service = mock(NetWorkApi::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    val item =
        TestUtil.getUser()

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.usersDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = UsersRepository(InstantAppExecutors(), dao, service)
    }


    @Test
    fun loadUsersListResponse() {
        val dbData = MutableLiveData<List<Users>>()
        `when`(dao.loadAllUsers()).thenReturn(dbData)

        val usersArrayList = TestUtil.createUserList(item)
        val call = ApiUtil.successCall(usersArrayList)

        `when`(service.getUsers()).thenReturn(call)

        val data = repository.getUserList()
        verify(dao).loadAllUsers()
        verifyNoMoreInteractions(service)

        val observer = mock<Observer<Resource<List<Users>>>>()
        data.observeForever(observer)
        verifyNoMoreInteractions(service)
        verify(observer).onChanged(Resource.loading(null))

        val updatedDbData = MutableLiveData<List<Users>>()
        `when`(dao.loadAllUsers()).thenReturn(updatedDbData)

        dbData.postValue(null)
        verify(service).getUsers()
        verify(dao).insertUsers(usersArrayList)

    }
}


