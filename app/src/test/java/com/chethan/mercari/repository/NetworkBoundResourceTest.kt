package com.chethan.mercari.repository


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.chethan.mercari.api.ApiResponse
import com.chethan.mercari.util.CountingAppExecutors
import com.chethan.mercari.util.InstantAppExecutors
import com.chethan.mercari.util.mock
import com.chethan.mericari.util.ApiUtil
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mockito.*
import retrofit2.Response
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

/**
 * Created by Chethan on 7/30/2019.
 */

@RunWith(Parameterized::class)
class NetworkBoundResourceTest(private val useRealExecutors: Boolean) {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var handleSaveCallResult: (ProductCategory) -> Unit

    private lateinit var handleShouldMatch: (ProductCategory?) -> Boolean

    private lateinit var handleCreateCall: () -> LiveData<ApiResponse<ProductCategory>>

    private val dbData = MutableLiveData<ProductCategory>()

    private lateinit var networkBoundResource: NetworkBoundResource<ProductCategory, ProductCategory>

    private val fetchedOnce = AtomicBoolean(false)
    private lateinit var countingAppExecutors: CountingAppExecutors

    init {
        if (useRealExecutors) {
            countingAppExecutors = CountingAppExecutors()
        }
    }

    @Before
    fun init() {
        val appExecutors = if (useRealExecutors)
            countingAppExecutors.appExecutors
        else
            InstantAppExecutors()
        networkBoundResource = object : NetworkBoundResource<ProductCategory, ProductCategory>(appExecutors) {
            override fun saveCallResult(item: ProductCategory) {
                handleSaveCallResult(item)
            }

            override fun shouldFetch(data: ProductCategory?): Boolean {
                // since test methods don't handle repetitive fetching, call it only once
                return handleShouldMatch(data) && fetchedOnce.compareAndSet(false, true)
            }

            override fun loadFromDb(): LiveData<ProductCategory> {
                return dbData
            }

            override fun createCall(): LiveData<ApiResponse<ProductCategory>> {
                return handleCreateCall()
            }
        }
    }

    private fun drain() {
        if (!useRealExecutors) {
            return
        }
        try {
            countingAppExecutors.drainTasks(1, TimeUnit.SECONDS)
        } catch (t: Throwable) {
            throw AssertionError(t)
        }

    }

    @Test
    fun basicFromNetwork() {
        val saved = AtomicReference<ProductCategory>()
        handleShouldMatch = { it == null }
        val fetchedDbValue = ProductCategory(1)
        handleSaveCallResult = { foo ->
            saved.set(foo)
            dbData.setValue(fetchedDbValue)
        }
        val networkResult = ProductCategory(1)
        handleCreateCall = { ApiUtil.createCall(Response.success(networkResult)) }

        val observer = mock<Observer<Resource<ProductCategory>>>()
        networkBoundResource.asLiveData().observeForever(observer)
        drain()
        verify(observer).onChanged(Resource.loading(null))
        reset(observer)
        dbData.value = null
        drain()
        assertThat(saved.get(), `is`(networkResult))
        verify(observer).onChanged(Resource.success(fetchedDbValue))
    }

    @Test
    fun failureFromNetwork() {
        val saved = AtomicBoolean(false)
        handleShouldMatch = { it == null }
        handleSaveCallResult = {
            saved.set(true)
        }
        val body = ResponseBody.create(MediaType.parse("text/html"), "error")
        handleCreateCall = { ApiUtil.createCall(Response.error<ProductCategory>(500, body)) }

        val observer = mock<Observer<Resource<ProductCategory>>>()
        networkBoundResource.asLiveData().observeForever(observer)
        drain()
        verify(observer).onChanged(Resource.loading(null))
        reset(observer)
        dbData.value = null
        drain()
        assertThat(saved.get(), `is`(false))
        verify(observer).onChanged(Resource.error("error", null))
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun dbSuccessWithoutNetwork() {
        val saved = AtomicBoolean(false)
        handleShouldMatch = { it == null }
        handleSaveCallResult = {
            saved.set(true)
        }

        val observer = mock<Observer<Resource<ProductCategory>>>()
        networkBoundResource.asLiveData().observeForever(observer)
        drain()
        verify(observer).onChanged(Resource.loading(null))
        reset(observer)
        val dbFoo = ProductCategory(1)
        dbData.value = dbFoo
        drain()
        verify(observer).onChanged(Resource.success(dbFoo))
        assertThat(saved.get(), `is`(false))
        val dbFoo2 = ProductCategory(2)
        dbData.value = dbFoo2
        drain()
        verify(observer).onChanged(Resource.success(dbFoo2))
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun dbSuccessWithFetchFailure() {
        val dbValue = ProductCategory(1)
        val saved = AtomicBoolean(false)
        handleShouldMatch = { foo -> foo === dbValue }
        handleSaveCallResult = {
            saved.set(true)
        }
        val body = ResponseBody.create(MediaType.parse("text/html"), "error")
        val apiResponseLiveData = MutableLiveData<ApiResponse<ProductCategory>>()
        handleCreateCall = { apiResponseLiveData }

        val observer = mock<Observer<Resource<ProductCategory>>>()
        networkBoundResource.asLiveData().observeForever(observer)
        drain()
        verify(observer).onChanged(Resource.loading(null))
        reset(observer)

        dbData.value = dbValue
        drain()
        verify(observer).onChanged(Resource.loading(dbValue))

        apiResponseLiveData.value = ApiResponse.create(Response.error<ProductCategory>(400, body))
        drain()
        assertThat(saved.get(), `is`(false))
        verify(observer).onChanged(Resource.error("error", dbValue))

        val dbValue2 = ProductCategory(2)
        dbData.value = dbValue2
        drain()
        verify(observer).onChanged(Resource.error("error", dbValue2))
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun dbSuccessWithReFetchSuccess() {
        val dbValue = ProductCategory(1)
        val dbValue2 = ProductCategory(2)
        val saved = AtomicReference<ProductCategory>()
        handleShouldMatch = { foo -> foo === dbValue }
        handleSaveCallResult = { foo ->
            saved.set(foo)
            dbData.setValue(dbValue2)
        }
        val apiResponseLiveData = MutableLiveData<ApiResponse<ProductCategory>>()
        handleCreateCall = { apiResponseLiveData }

        val observer = mock<Observer<Resource<ProductCategory>>>()
        networkBoundResource.asLiveData().observeForever(observer)
        drain()
        verify(observer).onChanged(Resource.loading(null))
        reset(observer)

        dbData.value = dbValue
        drain()
        val networkResult = ProductCategory(1)
        verify(observer).onChanged(Resource.loading(dbValue))
        apiResponseLiveData.value = ApiResponse.create(Response.success(networkResult))
        drain()
        assertThat(saved.get(), `is`(networkResult))
        verify(observer).onChanged(Resource.success(dbValue2))
        verifyNoMoreInteractions(observer)
    }

    private data class ProductCategory(var value: Int)

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun param(): List<Boolean> {
            return arrayListOf(true, false)
        }
    }
}