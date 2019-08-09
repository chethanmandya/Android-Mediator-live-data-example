package com.chethan.babylon

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import com.chethan.babylon.model.Posts
import com.chethan.babylon.util.LiveDataTestUtil.getValue
import com.chethan.babylon.util.TestUtil
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PostsDaoTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    val item = TestUtil.getUserPost()


    @Test
    fun insertAndRead() {

        // insert data and load data
        val itemArrayList: List<Posts> = TestUtil.createUserPostArrayList(item)
        db.postsDao().insertPosts(itemArrayList)

        // check data is not null
        val loaded = getValue(db.postsDao().loadAllPost())
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())

        // check data element
        val posts = loaded.get(0)
        MatcherAssert.assertThat(posts.id, CoreMatchers.`is`("1"))
        MatcherAssert.assertThat(posts.userId, CoreMatchers.`is`("1"))
        MatcherAssert.assertThat(
            posts.title,
            CoreMatchers.`is`("sunt aut facere repellat provident occaecati excepturi optio reprehenderit 1")
        )
        MatcherAssert.assertThat(
            posts.body,
            CoreMatchers.`is`("quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto")
        )

    }


    @Test
    fun createIfNotExists_exists() {
        db.postsDao().insertPost(item)
        MatcherAssert.assertThat(db.postsDao().createPostIfNotExists(item), CoreMatchers.`is`(-1L))
    }

    @Test
    fun createIfNotExists_doesNotExist() {
        MatcherAssert.assertThat(db.postsDao().createPostIfNotExists(item), CoreMatchers.`is`(1L))
    }


    @Test
    fun deleteSingleItem() {
        // insert 10 items
        val itemArrayList: List<Posts> = TestUtil.createUserPostArrayList(item)
        db.postsDao().insertPosts(itemArrayList)
        // Delete single item, Number of item present this time is 10
        db.postsDao().delete(item)
        // check data size after deletion
        val listAfterDelete = getValue(db.postsDao().loadAllPost())
        MatcherAssert.assertThat(listAfterDelete.size, CoreMatchers.`is`(99))

    }


    @Test
    fun deleteAll() {
        // Delete all the items
        db.postsDao().deleteAll()
        // check data size after deletion
        val listOfItemsAfterDeletingAll = getValue(db.postsDao().loadAllPost())
        MatcherAssert.assertThat(listOfItemsAfterDeletingAll.size, CoreMatchers.`is`(0))
    }

    @Test
    fun insertItem() {
        // insertion operation
        db.postsDao().insertPost(item)
        // check data size, it should be 1
        val listAfterInsert = getValue(db.postsDao().loadAllPost())
        MatcherAssert.assertThat(listAfterInsert.size, CoreMatchers.`is`(1))
    }


}
