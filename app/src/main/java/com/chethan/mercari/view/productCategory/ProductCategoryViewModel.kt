package com.chethan.mercari.view.productCategory


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chethan.mercari.model.ProductCategory
import com.chethan.mercari.repository.CategoryListRepository
import com.chethan.mercari.repository.Resource
import com.chethan.mercari.testing.OpenForTesting
import javax.inject.Inject

/**
 * Created by Chethan on 7/30/2019.
 */
@OpenForTesting
class ProductCategoryViewModel @Inject constructor(categoryListRepository: CategoryListRepository) : ViewModel() {

    val productCategories: LiveData<Resource<List<ProductCategory>>> =
        categoryListRepository.getProductCategotyJson()

}


