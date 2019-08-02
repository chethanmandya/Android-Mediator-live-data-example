package com.chethan.mercari.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chethan.mercari.view.Products.ProductsViewModel
import com.chethan.mercari.view.productCategory.ProductCategoryViewModel
import com.chethan.mercari.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProductCategoryViewModel::class)
    abstract fun bindProductCategoryViewModel(productCategoryViewModel: ProductCategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductsViewModel::class)
    abstract fun bindProductsViewModel(productsViewModel: ProductsViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
