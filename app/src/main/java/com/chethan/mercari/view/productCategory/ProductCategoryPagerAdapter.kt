package com.chethan.mercari.view.productCategory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.chethan.mercari.model.ProductCategory
import com.chethan.mercari.view.Products.ProductsFragment


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class ProductCategoryPagerAdapter(private val productCategoryList: List<ProductCategory>, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return ProductsFragment.newInstance(productCategoryList[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return productCategoryList.get(position).name
    }

    override fun getCount(): Int {
        return productCategoryList.size
    }
}