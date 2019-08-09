package com.chethan.babylon.util

import com.chethan.babylon.model.Comments
import com.chethan.babylon.model.Posts
import com.chethan.babylon.model.Users


/**
 * Created by Chethan on 7/30/2019.
 */
object TestUtil {


    fun getUser(): Users {
        return createUserItem(
            "1", "Leanne Graham",
            "Bret",
            "Sincere@april.biz",
            "1-770-736-8031 x56442",
            "hildegard.org",
            ""
        )
    }

    fun getUserPost(): Posts {
        return createUserPostItem(
            "1", "1", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "" +
                    "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"
        )
    }

    fun getUserCommentItem(): Comments {
        return createCommentItem(
            "1",
            "1",
            "id labore ex et quam laborum",
            "Eliseo@gardner.biz",
            "laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"
        )
    }

    fun createUserPostItem(
        userId: String,
        id: String,
        title: String,
        body: String
    ) = Posts(
        userId,
        id,
        title,
        body
    )


    fun createUserPostArrayList(item: Posts): List<Posts> {
        val list: ArrayList<Posts> = arrayListOf<Posts>()
        for (i in 1..100)
        list.add(
            createUserPostItem(
                i.toString(),
                i.toString(),
                item.title + " "+ i.toString(),
                item.body
            )
        )
        return list
    }

    /**
     *  User Comments
     */
    fun createUserCommentsArrayList(item: Comments): List<Comments> {
        val list: ArrayList<Comments> = arrayListOf<Comments>()
        for (i in 1..10)
            list.add(
                createCommentItem(
                    i.toString(),
                    i.toString(),
                    item.name,
                    item.email,
                    item.body
                )
            )
        return list
    }


    fun createCommentItem(
        postId: String,
        id: String,
        name: String,
        email: String,
        body: String
    ) = Comments(
        postId = postId,
        id = id,
        name = name,
        email = email,
        body = body
    )


    /**
     *  Users
     */

    fun createUserList(item: Users): List<Users> {
        val list: ArrayList<Users> = arrayListOf<Users>()
        for (i in 1..10)
            list.add(
                createUserItem(
                    item.id,
                    item.name,
                    item.username,
                    item.email,
                    item.phone,
                    item.website,
                    item.company
                )
            )
        return list
    }


    fun createUserItem(
        id: String,
        name: String,
        username: String,
        email: String,
        phone: String,
        website: String,
        company: String
    ) = Users(
        id = id,
        name = name,
        username = username,
        email = email,
        phone = phone,
        website = website,
        company = company
    )

}



