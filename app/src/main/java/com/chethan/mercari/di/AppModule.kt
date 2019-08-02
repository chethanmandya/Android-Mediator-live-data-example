package com.chethan.mercari.di

import android.app.Application
import androidx.room.Room
import com.chethan.demoproject.utils.LiveDataCallAdapterFactory
import com.chethan.mercari.API_REST_URL
import com.chethan.mercari.api.NetWorkApi
import com.chethan.mercari.db.AppDatabase
import com.chethan.mercari.db.ProductCategoryDao
import com.chethan.mercari.db.ProductsDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGithubService(): NetWorkApi {
        val retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .baseUrl(API_REST_URL)
                .build()
        return retrofit.create(NetWorkApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "Mercari.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideProductsDao(db: AppDatabase): ProductsDao {
        return db.productsDao()
    }


    @Singleton
    @Provides
    fun provideProductCategoryDao(db: AppDatabase): ProductCategoryDao {
        return db.productCategoryDao()
    }

}
