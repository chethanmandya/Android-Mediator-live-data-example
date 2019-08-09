package com.chethan.babylon.repository

import com.chethan.babylon.repository.Status.*

/**
 * Created by Chethan on 7/30/2019.
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 */

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }
}
