package com.chethan.babylon.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.chethan.babylon.API_REST_URL
import com.chethan.babylon.model.Comments
import com.chethan.babylon.model.Posts
import com.chethan.babylon.model.Users
import com.chethan.babylon.util.LiveDataTestUtil.getValue
import com.chethan.demoproject.utils.LiveDataCallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.junit.After
import org.junit.Test

/**
 * Created by Chethan on 8/9/2019.
 */

@RunWith(JUnit4::class)
class ApiServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: NetWorkApi

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(NetWorkApi::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }


    @Test
    fun getComments() {
        enqueueResponse("comments.json")
        val body = (getValue(service.getComments()) as ApiSuccessResponse).body

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/comments"))

        assertThat<List<Comments>>(body, notNullValue())
        assertThat(body[0].postId, `is`("1"))
        assertThat(body[0].id, `is`("1"))
        assertThat(body[0].name, `is`("id labore ex et quam laborum"))
    }


    @Test
    fun getPosts() {
        enqueueResponse("posts.json")
        val body = (getValue(service.getPost()) as ApiSuccessResponse).body

        val request = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/posts"))

        assertThat<List<Posts>>(body, notNullValue())
        assertThat(body[0].id, `is`("1"))
        assertThat(body[0].title, `is`("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))
        assertThat(body[0].userId, `is`("1"))
    }




    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}
