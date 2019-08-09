package com.chethan.babylon.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Chethan on 8/4/2019.
 */

@Entity(primaryKeys = ["id"])
data class Users(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("website")
    val website: String,

    @field:SerializedName("company")
    val company: String

) : Serializable {
}