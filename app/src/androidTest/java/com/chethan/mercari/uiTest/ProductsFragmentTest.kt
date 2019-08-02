package com.chethan.mercari.uiTest

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.chethan.mercari.R
import com.chethan.mercari.api.binding.FragmentBindingAdapters
import com.chethan.mercari.di.Injectable
import com.chethan.mercari.model.ProductOverview
import com.chethan.mercari.repository.Resource
import com.chethan.mercari.testing.SingleFragmentActivity
import com.chethan.mercari.util.DataBindingIdlingResourceRule
import com.chethan.mercari.util.TestUtil
import com.chethan.mercari.view.Products.ProductsFragment
import com.chethan.mercari.view.Products.ProductsViewModel
import com.chethan.mericari.util.ViewModelUtil
import org.hamcrest.CoreMatchers.allOf
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
class ProductsFragmentTest : Fragment(), Injectable {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java, true, true)

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)

    private val listOfCategory = TestUtil.createProductCategoryArrayList(TestUtil.getCategoryItem())
    private val listOfProducts = TestUtil.createProductOverviewArrayList(TestUtil.getProductsItem())

    private lateinit var mockBindingAdapter: FragmentBindingAdapters
    private lateinit var productsViewModel: ProductsViewModel
    private val productsResult = MutableLiveData<Resource<List<ProductOverview>>>()
    private val productsFragment = ProductsFragment.newInstance(listOfCategory[0])

    @Before
    fun init() {
        productsViewModel = Mockito.mock(ProductsViewModel::class.java)
        Mockito.`when`(productsViewModel.products).thenReturn(productsResult)
        mockBindingAdapter = Mockito.mock(FragmentBindingAdapters::class.java)
        productsFragment.viewModelFactory = ViewModelUtil.createFor(productsViewModel)
        activityRule.activity.setFragment(productsFragment)
        productsResult.postValue(Resource.success(listOfProducts))
    }

    @Test
    fun clickGridItem() {
        onData(anything()).atPosition(0).perform(click());
    }


    @Test
    fun validateGridItem() {
        onView(allOf(withId(R.id.tvNumberOfLike), withText("91")))
        onView(allOf(withId(R.id.tvNumberOfComments), withText("59")))
        onView(allOf(withId(R.id.tvNumberOfPrice), withText("51")))
    }

//    @Test
//    fun addGridItems() {
//        listOfProducts.
//    }
}