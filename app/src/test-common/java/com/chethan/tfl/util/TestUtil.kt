package com.chethan.mercari.util

import com.chethan.mercari.model.ProductCategory
import com.chethan.mercari.model.ProductOverview


/**
 * Created by Chethan on 7/30/2019.
 */
object TestUtil {


    fun getCategoryItem(): ProductCategory {
        return createProductCategoryItem("All", "https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/all.json")
    }

    fun getProductsItem(): ProductOverview {
        return createProductItem(
            "mmen1",
            "men1",
            "on_sale",
            "91",
            "59",
            "51",
            "https://dummyimage.com/400x400/000/fff?text=men1"
        )
    }

    fun createProductItem(
        id: String,
        name: String,
        status: String,
        numLikes: String,
        numComments: String,
        price: String,
        photo: String
    ) = ProductOverview(
        id = id,
        name = name,
        status = status,
        num_likes = numLikes,
        num_comments = numComments,
        price = price,
        photo = photo
    )

    fun createProductCategoryItem(
        name: String,
        data: String
    ) = ProductCategory(
        name = name,
        data = data
    )

    fun createProductCategoryArrayList(item: ProductCategory): List<ProductCategory> {
        val list: ArrayList<ProductCategory> = arrayListOf<ProductCategory>()
        list.add(
            ProductCategory(
                item.name,
                item.data!!
            )
        )
        return list
    }

    fun createProductOverviewArrayList(item: ProductOverview): List<ProductOverview> {
        val list: ArrayList<ProductOverview> = arrayListOf<ProductOverview>()
        for (i in 1..10)
            list.add(
                createProductItem(
                    "mmen" + i,
                    item.name!!,
                    item.status,
                    item.num_likes,
                    item.num_comments,
                    item.price,
                    item.photo
                )
            )


        return list
    }
}



