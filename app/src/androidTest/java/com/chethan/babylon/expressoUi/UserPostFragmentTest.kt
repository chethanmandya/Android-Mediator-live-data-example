package com.chethan.babylon.expressoUi

import androidx.databinding.DataBindingComponent
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
import com.chethan.babylon.model.Posts
import com.chethan.babylon.repository.Resource
import com.chethan.babylon.testing.SingleFragmentActivity
import com.chethan.babylon.util.*
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
class UserPostFragmentTest {
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

    private val listOfUserPost = TestUtil.createUserPostArrayList(TestUtil.getUserPost())

    private lateinit var mockBindingAdapter: FragmentBindingAdapters
    private lateinit var viewModel: UserPostViewModel
    private val results = MutableLiveData<Resource<List<Posts>>>()
    private val userPostFragment = TestUserPostFragment()

    @Before
    fun init() {
        viewModel = mock(UserPostViewModel::class.java)

        `when`(viewModel.userPosts).thenReturn(results)

        mockBindingAdapter = mock(FragmentBindingAdapters::class.java)

        userPostFragment.appExecutors = countingAppExecutors.appExecutors
        userPostFragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
        userPostFragment.dataBindingComponent = object : DataBindingComponent {
            override fun getFragmentBindingAdapters(): FragmentBindingAdapters {
                return mockBindingAdapter
            }
        }
        activityRule.activity.setFragment(userPostFragment)
        EspressoTestUtil.disableProgressBarAnimations(activityRule)
    }

    @Test
    fun testDataLoading() {
        // Check progress bar displaying before retrieving content from the net
        results.postValue(Resource.loading(null))
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
    }

    @Test
    fun loadResults() {
        // load content
        results.postValue(Resource.success(listOfUserPost))
        // select very first item of list
        var view = listMatcher().atPosition(0)
        // check item 1 in the list is displaying this content
        onView(view).check(matches(hasDescendant(withText("Title : sunt aut facere repellat provident occaecati excepturi optio reprehenderit 1"))))
        // check progress bar is dismissed after content is loaded
        onView(withId(R.id.progress_bar)).check(matches(CoreMatchers.not(isDisplayed())))
    }

    @Test
    fun error() {
        results.postValue(Resource.error("failed to load", null))
        onView(withId(R.id.error_msg)).check(matches(isDisplayed()))
    }

    //
    @Test
    fun navigateToDetailScreen() {
        // load network response
        results.postValue(Resource.success(listOfUserPost))
        // select first view in the list
        var view = listMatcher().atPosition(0)
        // perform click to the item in the list
        onView(view).perform(ViewActions.click())
        // verify screen is navigated to detail screen
        verify(userPostFragment.navController).navigate(
            UserPostFragmentDirections.showDetailsScreen(listOfUserPost[0].id, listOfUserPost[0].title)
        )
    }


    private fun listMatcher(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.post_list)
    }

    class TestUserPostFragment : UserPostFragment() {
        val navController = mock<NavController>()
        override fun navController() = navController
    }
}