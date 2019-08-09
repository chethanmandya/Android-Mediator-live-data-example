package com.chethan.babylon.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.chethan.babylon.api.NetWorkApi
import com.chethan.babylon.db.AppDatabase
import com.chethan.babylon.db.CommentsDao
import com.chethan.babylon.db.PostsDao
import com.chethan.babylon.model.Comments
import com.chethan.babylon.model.PostWithComments
import com.chethan.babylon.model.Posts
import com.chethan.babylon.repository.Resource
import com.chethan.babylon.repository.UserCommentsRepository
import com.chethan.babylon.repository.UserPostRepository
import com.chethan.babylon.testing.OpenForTesting
import com.chethan.babylon.util.InstantAppExecutors
import com.chethan.babylon.util.TestUtil
import com.chethan.babylon.util.mock
import com.chethan.babylon.view.postDetail.UserPostDetailViewModel
import com.chethan.babylon.view.userposts.UserPostViewModel
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.never

/**
 * Created by Chethan on 7/30/2019.
 *
 * User post detail has three different data to display on the screen, Post, User Comments, User
 * In order to make
 */
@RunWith(JUnit4::class)
class UserPostDetailViewModelTest {

    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var userPostDetailViewModel: UserPostDetailViewModel


    private lateinit var userPostRepository: UserPostRepository
    private lateinit var userCommentsRepository: UserCommentsRepository

    private val postsDao = Mockito.mock(PostsDao::class.java)
    private val commentsDao = Mockito.mock(CommentsDao::class.java)

    private val service = Mockito.mock(NetWorkApi::class.java)
    val item =
        TestUtil.getUserPost()

    @Before
    fun init() {
        val db = Mockito.mock(AppDatabase::class.java)

        //mock the call
        val dbData = MutableLiveData<List<Posts>>()
        Mockito.`when`(postsDao.loadAllPost()).thenReturn(dbData)

        userPostRepository = UserPostRepository(InstantAppExecutors(), postsDao, service)
        userCommentsRepository = UserCommentsRepository(InstantAppExecutors(), commentsDao, service)
        userPostDetailViewModel = UserPostDetailViewModel(userPostRepository, userCommentsRepository)
    }


    @Test
    fun testNotNull() {
        MatcherAssert.assertThat(userPostDetailViewModel.result, CoreMatchers.notNullValue())
    }

    @Test
    fun fetchWhenObserved() {
        val result = mock<Observer<Resource<PostWithComments>>>()
        userPostDetailViewModel.result.observeForever(result)
    }

}