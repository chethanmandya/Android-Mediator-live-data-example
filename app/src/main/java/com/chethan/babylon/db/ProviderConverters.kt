package com.chethan.babylon.db

import androidx.room.TypeConverter
import timber.log.Timber

/**
 * Created by Chethan on 5/4/2019.
 */


object ProviderConverters {
    @TypeConverter
    @JvmStatic
    fun stringToList(data: String?): List<Int>? {
        return data?.let {
            it.split(",").map {
                try {
                    it.toInt()
                } catch (ex: NumberFormatException) {
                    Timber.e(ex, "Cannot convert $it to number")
                    null
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter
    @JvmStatic
    fun intListToString(ints: List<Int>?): String? {
        return ints?.joinToString(",")
    }
}
