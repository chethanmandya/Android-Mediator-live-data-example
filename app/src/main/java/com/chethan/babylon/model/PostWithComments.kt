package com.chethan.babylon.model

import java.io.Serializable

/**
 * Created by Chethan on 8/4/2019.
 */


data class PostWithComments(
    val post: Posts,
    val comments: List<Comments>
) : Serializable {
}