package com.chethan.mercari.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Chethan on 7/27/2019.
 */

@Entity(primaryKeys = ["name"])
data class ProductCategory(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("data")
    val data: String
) : Serializable {
}