package com.chethan.mercari

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.runner.AndroidJUnit4
import com.chethan.mercari.model.ProductCategory
import com.chethan.mercari.util.LiveDataTestUtil.getValue
import com.chethan.mercari.util.TestUtil
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ProductCategoryDaoTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    val item = TestUtil.getCategoryItem()


    @Test
    fun insertAndRead() {

        // insert data and load data
        val itemArrayList: List<ProductCategory> = TestUtil.createProductCategoryArrayList(item)
        db.productCategoryDao().insertProductCategories(itemArrayList)

        // check data is not null
        val loaded = getValue(db.productCategoryDao().loadAllTheProductCategory())
        MatcherAssert.assertThat(loaded, CoreMatchers.notNullValue())

        // check data element
        val productCategory = loaded.get(0)
        MatcherAssert.assertThat(productCategory.name, CoreMatchers.`is`("All"))
        MatcherAssert.assertThat(productCategory.data, CoreMatchers.`is`("https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/all.json"))
         }


    @Test
    fun createIfNotExists_exists() {
        db.productCategoryDao().insertProductCategory(item)
        MatcherAssert.assertThat(db.productCategoryDao().createProductCategoryIfNotExists(item), CoreMatchers.`is`(-1L))
    }

    @Test
    fun createIfNotExists_doesNotExist() {
        MatcherAssert.assertThat(db.productCategoryDao().createProductCategoryIfNotExists(item), CoreMatchers.`is`(1L))
    }


    @Test
    fun deleteSingleItem() {
        // insert 10 items
        val itemArrayList: List<ProductCategory> = TestUtil.createProductCategoryArrayList(item)
        db.productCategoryDao().insertProductCategories(itemArrayList)
        // Delete single item, Number of item present this time is 10
        db.productCategoryDao().delete(item)
        // check data size after deletion
        val listAfterDelete = getValue(db.productCategoryDao().loadAllTheProductCategory())
        MatcherAssert.assertThat(listAfterDelete.size, CoreMatchers.`is`(0))

    }


    @Test
    fun deleteAll() {
        // Delete all the items
        db.productCategoryDao().deleteAll()
        // check data size after deletion
        val listOfItemsAfterDeletingAll = getValue(db.productCategoryDao().loadAllTheProductCategory())
        MatcherAssert.assertThat(listOfItemsAfterDeletingAll.size, CoreMatchers.`is`(0))
    }

    @Test
    fun insertItem() {
        // insertion operation
        db.productCategoryDao().insertProductCategory(item)
        // check data size, it should be 1
        val listAfterInsert = getValue(db.productCategoryDao().loadAllTheProductCategory())
        MatcherAssert.assertThat(listAfterInsert.size, CoreMatchers.`is`(1))
    }


}
