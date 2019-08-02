package com.chethan.mercari.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Chethan on 7/27/2019.
 */


@Entity(primaryKeys = ["id"])
data class ProductOverview(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("num_likes")
    val num_likes: String,

    @field:SerializedName("num_comments")
    val num_comments: String,

    @field:SerializedName("price")
    val price: String,

    @field:SerializedName("photo")
    val photo: String

) : Serializable {
}