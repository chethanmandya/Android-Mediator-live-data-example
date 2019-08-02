package com.chethan.mercari.view.Products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.chethan.mercari.R
import com.chethan.mercari.api.binding.FragmentDataBindingComponent
import com.chethan.mercari.databinding.CategoryProductsBinding
import com.chethan.mercari.di.Injectable
import com.chethan.mercari.model.ProductCategory
import com.chethan.mercari.testing.OpenForTesting
import com.chethan.mercari.utils.autoCleared
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
@OpenForTesting
class ProductsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var productsViewModel: ProductsViewModel

    var binding by autoCleared<CategoryProductsBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<CategoryProductsBinding>(
            inflater,
            R.layout.category_products,
            container,
            false,
            dataBindingComponent
        )

        binding = dataBinding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val categoryUrl = arguments?.getString(CATEGORY_URL)
        val categoryName = arguments?.getString(CATEGORY_NAME)

        productsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(categoryName!!, ProductsViewModel::class.java)
        productsViewModel.getData(categoryUrl!!, categoryName)
        binding.setLifecycleOwner(viewLifecycleOwner)
        productsViewModel.products.observe(viewLifecycleOwner, Observer { result ->

            if (result.data != null)
                context?.let {
                    if (result.data.size > 0) {
                        val productGridAdapter = ProductGridAdapter(it, result.data)
                        binding.productsGridView.adapter = productGridAdapter
                    }
                }
        })


    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val CATEGORY_NAME = "category_name"
        private const val CATEGORY_URL = "category_url"


        @JvmStatic
        fun newInstance(productCategory: ProductCategory): ProductsFragment {
            return ProductsFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_NAME, productCategory.name.toString())
                    putString(CATEGORY_URL, productCategory.data.toString())
                }
            }
        }
    }
}