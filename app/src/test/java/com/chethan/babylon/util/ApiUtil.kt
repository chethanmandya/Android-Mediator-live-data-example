package com.chethan.babylon.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chethan.babylon.api.ApiResponse
import retrofit2.Response

/**
 * Created by Chethan on 7/30/2019.
 */

object ApiUtil {
    fun <T : Any> successCall(data: T) = createCall(Response.success(data))

    fun <T : Any> createCall(response: Response<T>) = MutableLiveData<ApiResponse<T>>().apply {
        value = ApiResponse.create(response)
    } as LiveData<ApiResponse<T>>
}
