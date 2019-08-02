package com.chethan.mercari.di

import com.chethan.mercari.view.Products.ProductsFragment
import com.chethan.mercari.view.productCategory.ProductCategoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun userProductCategoryFragment(): ProductCategoryFragment

    @ContributesAndroidInjector
    abstract fun userProductsFragment(): ProductsFragment
}
