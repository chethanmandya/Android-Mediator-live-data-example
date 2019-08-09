package com.chethan.babylon.expressoUi

import androidx.databinding.DataBindingComponent
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.chethan.babylon.R
import com.chethan.babylon.api.binding.FragmentBindingAdapters
import com.chethan.babylon.model.PostWithComments
import com.chethan.babylon.model.Posts
import com.chethan.babylon.repository.Resource
import com.chethan.babylon.testing.SingleFragmentActivity
import com.chethan.babylon.util.*
import com.chethan.babylon.view.postDetail.UserPostDetailFragment
import com.chethan.babylon.view.postDetail.UserPostDetailFragmentArgs
import com.chethan.babylon.view.postDetail.UserPostDetailViewModel
import com.chethan.babylon.view.userposts.UserPostFragment
import com.chethan.babylon.view.userposts.UserPostFragmentDirections
import com.chethan.babylon.view.userposts.UserPostViewModel
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matchers.hasEntry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*


@RunWith(AndroidJUnit4::class)
class UserPostDetailsFragmentTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java, true, true)
    @Rule
    @JvmField
    val executorRule = TaskExecutorWithIdlingResourceRule()
    @Rule
    @JvmField
    val countingAppExecutors = CountingAppExecutorsRule()
    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)

    private val listOfUserComments = TestUtil.createUserCommentsArrayList(TestUtil.getUserCommentItem())
    private val userPost = TestUtil.getUserPost()
    private val postWithComments = PostWithComments(userPost, listOfUserComments)

    private lateinit var mockBindingAdapter: FragmentBindingAdapters
    private lateinit var viewModel: UserPostDetailViewModel
    private val results = MediatorLiveData<Resource<PostWithComments>>()
    private val userPostDetailFragment = TestUserPostDetailFragment().apply {
        arguments = UserPostDetailFragmentArgs("", "b").toBundle()
    }

    @Before
    fun init() {
        viewModel = mock(UserPostDetailViewModel::class.java)

        `when`(viewModel.result).thenReturn(results)

        mockBindingAdapter = mock(FragmentBindingAdapters::class.java)

        userPostDetailFragment.appExecutors = countingAppExecutors.appExecutors
        userPostDetailFragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
        userPostDetailFragment.dataBindingComponent = object : DataBindingComponent {
            override fun getFragmentBindingAdapters(): FragmentBindingAdapters {
                return mockBindingAdapter
            }
        }

        activityRule.activity.setFragment(userPostDetailFragment)
        EspressoTestUtil.disableProgressBarAnimations(activityRule)
    }



    @Test
    fun loadResults() {
        // load content
        results.postValue(Resource.success(postWithComments))
        // select very first item of list
        var view = listMatcher().atPosition(0)
        // check item 1 in the list is displaying this content
        onView(view).check(matches(hasDescendant(withText("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))))
        // check progress bar is dismissed after content is loaded
        onView(withId(R.id.progress_bar)).check(matches(CoreMatchers.not(isDisplayed())))
    }


    @Test
    fun loadUserCommentsResults() {
        // load content
        results.postValue(Resource.success(postWithComments))
        // select very first item of list
        var view = listMatcher().atPosition(1)
        // check item 1 in the list is displaying this content
        onView(view).check(matches(hasDescendant(withText("Name : id labore ex et quam laborum"))))
        // check progress bar is dismissed after content is loaded
        onView(withId(R.id.progress_bar)).check(matches(CoreMatchers.not(isDisplayed())))
    }






    private fun listMatcher(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.commentsList)
    }

    class TestUserPostDetailFragment : UserPostDetailFragment() {
        val navController = mock<NavController>()
    }
}