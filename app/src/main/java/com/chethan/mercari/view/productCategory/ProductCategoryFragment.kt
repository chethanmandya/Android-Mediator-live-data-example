package com.chethan.mercari.view.productCategory

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
import androidx.navigation.fragment.findNavController
import com.chethan.mercari.R
import com.chethan.mercari.api.binding.FragmentDataBindingComponent
import com.chethan.mercari.databinding.ProductCategoryFragmentBinding
import com.chethan.mercari.di.Injectable
import com.chethan.mercari.testing.OpenForTesting
import com.chethan.mercari.utils.autoCleared
import javax.inject.Inject

/**
 * Created by Chethan on 7/30/2019.
 */

@OpenForTesting
class ProductCategoryFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var productCategoryViewModel: ProductCategoryViewModel

    var binding by autoCleared<ProductCategoryFragmentBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<ProductCategoryFragmentBinding>(
            inflater,
            R.layout.product_category_fragment,
            container,
            false,
            dataBindingComponent
        )

        binding = dataBinding
        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productCategoryViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ProductCategoryViewModel::class.java)
        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.productCategory = productCategoryViewModel.productCategories
        productCategoryViewModel.productCategories.observe(viewLifecycleOwner, Observer { result ->

            if (result.data != null)
                context?.let {
                    if (result.data.size > 0) {
                        val sectionsPagerAdapter = ProductCategoryPagerAdapter(result.data, childFragmentManager)
                        binding.productCategoryViewPager.adapter = sectionsPagerAdapter
                        binding.tabs.setupWithViewPager(binding.productCategoryViewPager)
                    }
                }

        })


    }

    /**
     * Created to be able to override in tests
     */
    fun navController() = findNavController()
}