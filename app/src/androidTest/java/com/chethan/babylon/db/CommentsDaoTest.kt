package com.chethan.babylon

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import com.chethan.babylon.model.Comments
import com.chethan.babylon.util.LiveDataTestUtil.getValue
import com.chethan.babylon.util.TestUtil
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Interface for database access on comments related operations.
 */


@RunWith(AndroidJUnit4::class)
class CommentsDaoTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    val item =
        TestUtil.getUserCommentItem()

    @Test
    fun insertAndRead() {

        // insert data and load data
        val itemArrayList: List<Comments> = TestUtil.createUserCommentsArrayList(item)
        db.commentsDao().insertComments(itemArrayList)

        // check data is not null
        val loaded = getValue(db.commentsDao().loadAllTheComments())
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())

        // check data element
        val comment = loaded.get(0)
        MatcherAssert.assertThat(comment.postId, CoreMatchers.`is`("1"))
        MatcherAssert.assertThat(comment.id, CoreMatchers.`is`("1"))
        MatcherAssert.assertThat(comment.name, CoreMatchers.`is`("id labore ex et quam laborum"))
        MatcherAssert.assertThat(comment.email, CoreMatchers.`is`("Eliseo@gardner.biz"))
        MatcherAssert.assertThat(comment.body, CoreMatchers.`is`("laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium"))
   }


    @Test
    fun createIfNotExists_exists() {
        db.commentsDao().insertComment(item)
        MatcherAssert.assertThat(db.commentsDao().createCommentsIfNotExists(item), CoreMatchers.`is`(-1L))
    }

    @Test
    fun createIfNotExists_doesNotExist() {
        MatcherAssert.assertThat(db.commentsDao().createCommentsIfNotExists(item), CoreMatchers.`is`(1L))
    }


    @Test
    fun deleteSingleItem() {
        // insert 10 items
        val itemArrayList: List<Comments> = TestUtil.createUserCommentsArrayList(item)
        db.commentsDao().insertComments(itemArrayList)
        // Delete single item, Number of item present this time is 10
        db.commentsDao().delete(item)
        // check data size after deletion
        val listAfterDelete = getValue(db.commentsDao().loadAllTheComments())
        MatcherAssert.assertThat(listAfterDelete.size, CoreMatchers.`is`(9))

    }


    @Test
    fun deleteAll() {
        // Delete all the items
        db.commentsDao().deleteAll()
        // check data size after deletion
        val listOfItemsAfterDeletingAll = getValue(db.commentsDao().loadAllTheComments())
        MatcherAssert.assertThat(listOfItemsAfterDeletingAll.size, CoreMatchers.`is`(0))
    }

    @Test
    fun insertItem() {
        // insertion operation
        db.commentsDao().insertComment(item)
        // check data size, it should be 1
        val listAfterInsert = getValue(db.commentsDao().loadAllTheComments())
        MatcherAssert.assertThat(listAfterInsert.size, CoreMatchers.`is`(1))
    }


}
