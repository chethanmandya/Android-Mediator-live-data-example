package com.chethan.mercari.view.Products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.chethan.mercari.databinding.ProductEntryBinding
import com.chethan.mercari.model.ProductOverview


/**
 * Created by Chethan on 7/28/2019.
 */

class ProductGridAdapter(private val context: Context, private val listOfProducts: List<ProductOverview>) :
    BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val binding = ProductEntryBinding.inflate(inflater, null, false)
        binding.productOverview = listOfProducts[position]
        return binding.root
    }

    override fun getItem(position: Int): Any {
        return listOfProducts[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listOfProducts.size
    }


}