package com.chethan.babylon.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chethan.babylon.model.Posts
import com.chethan.babylon.testing.OpenForTesting

/**
 * Interface for database access on posts related operations.
 */
@Dao
@OpenForTesting
abstract class PostsDao {

    // to insert single posts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPost(vararg repos: Posts)

    // to insert multiple category posts, like men, all, women
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPosts(repositories: List<Posts>)

    // insert posts if not exist
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createPostIfNotExists(posts : Posts): Long

    // to delete post category
    @Delete
    abstract fun delete(item: Posts)

    // to delete whole category list
    @Query("DELETE FROM Posts")
    abstract fun deleteAll()


    @Query("SELECT * FROM Posts")
    abstract fun loadAllPost(): LiveData<List<Posts>>

    @Query("SELECT * FROM Posts where id = :userId")
    abstract fun loadPost(userId: String): LiveData<Posts>


}
