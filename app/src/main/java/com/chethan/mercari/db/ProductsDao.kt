package com.chethan.mercari.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.chethan.mercari.model.ProductOverview
import com.chethan.mercari.testing.OpenForTesting


@Dao
@OpenForTesting
abstract class ProductsDao {

    // to insert single product
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProduct(vararg repos: ProductOverview)

    // to insert multiple category products, like men, all, women
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProducts(repositories: List<ProductOverview>)

    // insert product category if not exist
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createProductIfNotExists(product: ProductOverview): Long

    // to delete product category
    @Delete
    abstract fun delete(item: ProductOverview)

    // to delete whole category list
    @Query("DELETE FROM ProductOverview")
    abstract fun deleteAll()

    @Query("SELECT * FROM ProductOverview where id LIKE '%' || :categoryName || '%'")
    abstract fun loadProducts(categoryName : String): LiveData<List<ProductOverview>>

    @Query("SELECT * FROM ProductOverview")
    abstract fun loadAllTheProducts(): LiveData<List<ProductOverview>>

}
