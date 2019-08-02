package com.chethan.mercari

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import com.chethan.mercari.model.ProductOverview
import com.chethan.mercari.util.LiveDataTestUtil.getValue
import com.chethan.mercari.util.TestUtil
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Interface for database access on product related operations.
 */


@RunWith(AndroidJUnit4::class)
class ProductsDaoTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    val item =
        TestUtil.getProductsItem()

    @Test
    fun insertAndRead() {

        // insert data and load data
        val itemArrayList: List<ProductOverview> = TestUtil.createProductOverviewArrayList(item)
        db.productsDao().insertProducts(itemArrayList)

        // check data is not null
        val loaded = getValue(db.productsDao().loadAllTheProducts())
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())

        // check data element
        val product = loaded.get(0)
        MatcherAssert.assertThat(product.id, CoreMatchers.`is`("mmen1"))
        MatcherAssert.assertThat(product.name, CoreMatchers.`is`("men1"))
        MatcherAssert.assertThat(product.status, CoreMatchers.`is`("on_sale"))
        MatcherAssert.assertThat(product.num_likes, CoreMatchers.`is`("91"))
        MatcherAssert.assertThat(product.num_comments, CoreMatchers.`is`("59"))
        MatcherAssert.assertThat(product.price, CoreMatchers.`is`("51"))
        MatcherAssert.assertThat(product.photo, CoreMatchers.`is`("https://dummyimage.com/400x400/000/fff?text=men1"))
    }


    @Test
    fun createIfNotExists_exists() {
        db.productsDao().insertProduct(item)
        MatcherAssert.assertThat(db.productsDao().createProductIfNotExists(item), CoreMatchers.`is`(-1L))
    }

    @Test
    fun createIfNotExists_doesNotExist() {
        MatcherAssert.assertThat(db.productsDao().createProductIfNotExists(item), CoreMatchers.`is`(1L))
    }


    @Test
    fun deleteSingleItem() {
        // insert 10 items
        val itemArrayList: List<ProductOverview> = TestUtil.createProductOverviewArrayList(item)
        db.productsDao().insertProducts(itemArrayList)
        // Delete single item, Number of item present this time is 10
        db.productsDao().delete(item)
        // check data size after deletion
        val listAfterDelete = getValue(db.productsDao().loadAllTheProducts())
        MatcherAssert.assertThat(listAfterDelete.size, CoreMatchers.`is`(9))

    }


    @Test
    fun deleteAll() {
        // Delete all the items
        db.productsDao().deleteAll()
        // check data size after deletion
        val listOfItemsAfterDeletingAll = getValue(db.productsDao().loadAllTheProducts())
        MatcherAssert.assertThat(listOfItemsAfterDeletingAll.size, CoreMatchers.`is`(0))
    }

    @Test
    fun insertItem() {
        // insertion operation
        db.productsDao().insertProduct(item)
        // check data size, it should be 1
        val listAfterInsert = getValue(db.productsDao().loadAllTheProducts())
        MatcherAssert.assertThat(listAfterInsert.size, CoreMatchers.`is`(1))
    }


}
