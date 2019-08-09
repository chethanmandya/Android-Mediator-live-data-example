package com.chethan.babylon.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Chethan on 8/4/2019.
 */


@Entity(primaryKeys = ["id"])
data class Comments(

    @field:SerializedName("postId")
    val postId: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("body")
    val body: String

) : Serializable {
}