package com.chethan.babylon

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import com.chethan.babylon.model.Users
import com.chethan.babylon.util.LiveDataTestUtil.getValue
import com.chethan.babylon.util.TestUtil
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UsersDaoTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    val item = TestUtil.getUser()


    @Test
    fun insertAndRead() {

        // insert data and load data
        val itemArrayList: List<Users> = TestUtil.createUserList(item)
        db.usersDao().insertUsers(itemArrayList)

        // check data is not null
        val loaded = getValue(db.usersDao().loadAllUsers())
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())

        // check data element
        val users = loaded.get(0)
        MatcherAssert.assertThat(users.id, CoreMatchers.`is`("1"))
        MatcherAssert.assertThat(users.name, CoreMatchers.`is`("Leanne Graham"))
        MatcherAssert.assertThat(users.username, CoreMatchers.`is`("Bret"))
        MatcherAssert.assertThat(users.email, CoreMatchers.`is`("Sincere@april.biz"))
        MatcherAssert.assertThat(users.phone, CoreMatchers.`is`("1-770-736-8031 x56442"))
        MatcherAssert.assertThat(users.website, CoreMatchers.`is`("hildegard.org"))
    }


    @Test
    fun createIfNotExists_exists() {
        db.usersDao().insertUser(item)
        MatcherAssert.assertThat(db.usersDao().createUsersIfNotExists(item), CoreMatchers.`is`(-1L))
    }

    @Test
    fun createIfNotExists_doesNotExist() {
        MatcherAssert.assertThat(db.usersDao().createUsersIfNotExists(item), CoreMatchers.`is`(1L))
    }


    @Test
    fun deleteSingleItem() {
        // insert 10 items
        val itemArrayList: List<Users> = TestUtil.createUserList(item)
        db.usersDao().insertUsers(itemArrayList)
        // Delete single item, Number of item present this time is 10
        db.usersDao().delete(item)
        // check data size after deletion
        val listAfterDelete = getValue(db.usersDao().loadAllUsers())
        MatcherAssert.assertThat(listAfterDelete.size, CoreMatchers.`is`(0))

    }


    @Test
    fun deleteAll() {
        // Delete all the items
        db.usersDao().deleteAll()
        // check data size after deletion
        val listOfItemsAfterDeletingAll = getValue(db.usersDao().loadAllUsers())
        MatcherAssert.assertThat(listOfItemsAfterDeletingAll.size, CoreMatchers.`is`(0))
    }

    @Test
    fun insertItem() {
        // insertion operation
        db.usersDao().insertUser(item)
        // check data size, it should be 1
        val listAfterInsert = getValue(db.usersDao().loadAllUsers())
        MatcherAssert.assertThat(listAfterInsert.size, CoreMatchers.`is`(1))
    }


}
