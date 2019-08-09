package com.chethan.babylon.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chethan.babylon.model.Comments
import com.chethan.babylon.testing.OpenForTesting

/**
 * Interface for database access on Comments related operations.
 */
@Dao
@OpenForTesting
abstract class CommentsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertComment(vararg repos: Comments)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertComments(repositories: List<Comments>)

    // insert comments category if not exist
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createCommentsIfNotExists(comments : Comments): Long

    @Delete
    abstract fun delete(item: Comments)

    @Query("DELETE FROM Comments")
    abstract fun deleteAll()


    @Query("SELECT * FROM Comments")
    abstract fun loadAllTheComments(): LiveData<List<Comments>>

    @Query("SELECT * FROM Comments where postId = :postId")
    abstract fun loadComments(postId : String): LiveData<List<Comments>>


}
