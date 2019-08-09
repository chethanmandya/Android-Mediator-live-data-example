package com.chethan.babylon.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chethan.babylon.model.Users
import com.chethan.babylon.testing.OpenForTesting


@Dao
@OpenForTesting
abstract class UsersDao {

    // to insert single users
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUser(vararg repos: Users)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUsers(repositories: List<Users>)

    // insert users if not exist
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createUsersIfNotExists(product: Users): Long

    // to delete users
    @Delete
    abstract fun delete(item: Users)

    // to delete whole category list
    @Query("DELETE FROM Users")
    abstract fun deleteAll()

    @Query("SELECT * FROM Users where id LIKE :userId")
    abstract fun loadUsers(userId: String): LiveData<List<Users>>

    @Query("SELECT * FROM Users")
    abstract fun loadAllUsers(): LiveData<List<Users>>

}
