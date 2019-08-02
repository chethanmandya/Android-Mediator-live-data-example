package com.chethan.mercari.api

import okhttp3.MediaType
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

/**
 * Created by Chethan on 7/30/2019.
 */

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test
    fun exception() {
        val exception = Exception("rotterdam")
        val (errorMessage) = ApiResponse.create<String>(exception)
        assertThat<String>(errorMessage, `is`("rotterdam"))
    }

    @Test
    fun success() {
        val apiResponse: ApiSuccessResponse<String> = ApiResponse
            .create<String>(Response.success("rotterdam")) as ApiSuccessResponse<String>
        assertThat<String>(apiResponse.body, `is`("rotterdam"))
        assertThat<Int>(apiResponse.nextPage, `is`(nullValue()))
    }

    @Test
    fun link() {
        val link =
            "<https://api.foursquare.com/v2/products/search?client_id=VVLRKMJWUSDI44T0SSZVLQ0FJRRC5ZXPWQAIQSKKPEKRXNDW\n" +
                    "  &client_secret=VTCYNUTZ3LHS31XDYEA0L0KC1ENXHCLIXCLNINJCUOLOMUBP\n" +
                    "  &near=Rotterdam\n" +
                    "&radius=1000\n" +
                    "  &v=20190503>"
        val headers = okhttp3.Headers.of("link", link)
        val response = ApiResponse.create<String>(Response.success("items", headers))
        assertThat<String>((response as ApiSuccessResponse).body, `is`("items"))
    }


    @Test
    fun badLinkHeader() {
        val link = "<https://api.foursquare.com/v2/products/search?client_id=VVLRKMJWUSDI44T0SSZVLQ0FJRRC5ZXPWQAIQSKKPEKRXNDW\n" +
                "  &client_secret=VTCYNUTZ3LHS31XDYEA0L0KC1ENXHCLIXCLNINJCUOLOMUBP\n" +
                "  &near=Rotterdam\n" +
                "&radius=1000\n" +
                "  &v=20190503>"
        val headers = okhttp3.Headers.of("link", link)
        val response = ApiResponse.create<String>(Response.success("items", headers))
        assertThat<Int>((response as ApiSuccessResponse).nextPage, nullValue())
    }

    @Test
    fun error() {
        val errorResponse = Response.error<String>(
            400,
            ResponseBody.create(MediaType.parse("application/txt"), "blah")
        )
        val (errorMessage) = ApiResponse.create<String>(errorResponse) as ApiErrorResponse<String>
        assertThat<String>(errorMessage, `is`("blah"))
    }
}