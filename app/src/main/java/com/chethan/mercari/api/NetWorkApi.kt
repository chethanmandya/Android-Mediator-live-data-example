package com.chethan.mercari.api

import androidx.lifecycle.LiveData
import com.chethan.mercari.model.ProductCategory
import com.chethan.mercari.model.ProductOverview
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by Chethan on 7/30/2019.
 * This interface contains the definition list of all the network endpoints used by the App.
 * Ref: Retrofit
 */
interface NetWorkApi {

    @GET("m-et/Android/json/master.json")
    fun getMasterJson(): LiveData<ApiResponse<List<ProductCategory>>>

    @GET
    fun getProducts(@Url url: String): LiveData<ApiResponse<List<ProductOverview>>>


}