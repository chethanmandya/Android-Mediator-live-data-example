package com.chethan.mercari.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chethan.mercari.model.ProductCategory
import com.chethan.mercari.testing.OpenForTesting

/**
 * Interface for database access on product related operations.
 */
@Dao
@OpenForTesting
abstract class ProductCategoryDao {

    // to insert single product
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProductCategory(vararg repos: ProductCategory)

    // to insert multiple category products, like men, all, women
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProductCategories(repositories: List<ProductCategory>)

    // insert product category if not exist
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createProductCategoryIfNotExists(product: ProductCategory): Long

    // to delete product category
    @Delete
    abstract fun delete(item: ProductCategory)

    // to delete whole category list
    @Query("DELETE FROM ProductCategory")
    abstract fun deleteAll()


    @Query("SELECT * FROM ProductCategory")
    abstract fun loadAllTheProductCategory(): LiveData<List<ProductCategory>>



}
