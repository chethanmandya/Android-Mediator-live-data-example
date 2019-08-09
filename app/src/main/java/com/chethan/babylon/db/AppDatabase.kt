package com.chethan.babylon.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chethan.babylon.model.Comments
import com.chethan.babylon.model.Posts
import com.chethan.babylon.model.Users

/**
 * Created by Chethan on 7/30/2019.
 */

@Database(
    entities = [
        Posts::class,
        Comments::class,
        Users::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
    abstract fun commentsDao(): CommentsDao
    abstract fun usersDao(): UsersDao
}
