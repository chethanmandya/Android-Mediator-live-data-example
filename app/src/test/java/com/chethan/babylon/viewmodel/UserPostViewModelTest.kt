package com.chethan.babylon.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.chethan.babylon.api.NetWorkApi
import com.chethan.babylon.db.AppDatabase
import com.chethan.babylon.db.PostsDao
import com.chethan.babylon.model.Posts
import com.chethan.babylon.repository.UserPostRepository
import com.chethan.babylon.repository.Resource
import com.chethan.babylon.testing.OpenForTesting
import com.chethan.babylon.util.InstantAppExecutors
import com.chethan.babylon.util.TestUtil
import com.chethan.babylon.util.mock
import com.chethan.babylon.view.userposts.UserPostViewModel
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import javax.inject.Inject

/**
 * Created by Chethan on 7/30/2019.
 */
@RunWith(JUnit4::class)
class UserPostViewModelTest {

    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var viewModel: UserPostViewModel


    private lateinit var repository: UserPostRepository
    private val dao = Mockito.mock(PostsDao::class.java)
    private val service = Mockito.mock(NetWorkApi::class.java)
    val item =
        TestUtil.getUserPost()

    @Before
    fun init() {
        val db = Mockito.mock(AppDatabase::class.java)

        //mock the call
        val dbData = MutableLiveData<List<Posts>>()
        Mockito.`when`(dao.loadAllPost()).thenReturn(dbData)

        repository = UserPostRepository(InstantAppExecutors(), dao, service)
        viewModel = UserPostViewModel(repository)
    }


    @Test
    fun testNotNull() {
        MatcherAssert.assertThat(viewModel.userPosts, CoreMatchers.notNullValue())
    }

    @Test
    fun fetchWhenObserved() {
        val result = mock<Observer<Resource<List<Posts>>>>()
        viewModel.userPosts.observeForever(result)
    }

}


