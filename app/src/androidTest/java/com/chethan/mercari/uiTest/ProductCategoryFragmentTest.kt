package com.chethan.mercari.uiTest

import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.chethan.mercari.R
import com.chethan.mercari.api.binding.FragmentBindingAdapters
import com.chethan.mercari.di.Injectable
import com.chethan.mercari.model.ProductCategory
import com.chethan.mercari.model.ProductOverview
import com.chethan.mercari.repository.Resource
import com.chethan.mercari.testing.SingleFragmentActivity
import com.chethan.mercari.util.*
import com.chethan.mercari.view.Products.ProductsFragment
import com.chethan.mercari.view.Products.ProductsViewModel
import com.chethan.mercari.view.productCategory.ProductCategoryFragment
import com.chethan.mercari.view.productCategory.ProductCategoryViewModel
import com.chethan.mericari.util.TaskExecutorWithIdlingResourceRule
import com.chethan.mericari.util.ViewModelUtil
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.anything
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


/**
 * Created by Chethan on 7/30/2019.
 */

@RunWith(AndroidJUnit4::class)
class ProductCategoryFragmentTest : Fragment(), Injectable {
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

    private lateinit var mockBindingAdapter: FragmentBindingAdapters
    private lateinit var viewModel: ProductCategoryViewModel
    private val results = MutableLiveData<Resource<List<ProductCategory>>>()
    private val productCategoryFragment = TestCategoryFragment()

    @Before
    fun init() {

        viewModel = Mockito.mock(ProductCategoryViewModel::class.java)
        Mockito.`when`(viewModel.productCategories).thenReturn(results)
        mockBindingAdapter = Mockito.mock(FragmentBindingAdapters::class.java)

        productCategoryFragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
        productCategoryFragment.dataBindingComponent = object : DataBindingComponent {
            override fun getFragmentBindingAdapters(): FragmentBindingAdapters {
                return mockBindingAdapter
            }
        }



        activityRule.activity.setFragment(productCategoryFragment)
        EspressoTestUtil.disableProgressBarAnimations(activityRule)
    }

    @Test
    fun search() {
        Espresso.onView(ViewMatchers.withId(R.id.progress_bar))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        results.postValue(Resource.loading(null))
        Espresso.onView(ViewMatchers.withId(R.id.progress_bar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    class TestCategoryFragment : ProductCategoryFragment() {
        val navController = mock<NavController>()
        override fun navController() = navController
    }


    @Test
    fun TestFab() {
        onView(withId(R.id.fab)).perform(click())
    }


}