package com.chethan.babylon.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Chethan on 8/4/2019.
 */

@Entity(primaryKeys = ["id"])
data class Posts(

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("body")
    val body: String

) : Serializable {
}