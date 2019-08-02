package com.chethan.mercari.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chethan.mercari.model.ProductCategory
import com.chethan.mercari.model.ProductOverview

/**
 * Created by Chethan on 7/30/2019.
 */

@Database(
    entities = [
        ProductCategory::class,
        ProductOverview::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun productCategoryDao(): ProductCategoryDao
    abstract fun productsDao(): ProductsDao
}
