package com.chethan.babylon.di

import android.app.Application
import androidx.room.Room
import com.chethan.demoproject.utils.LiveDataCallAdapterFactory
import com.chethan.babylon.API_REST_URL
import com.chethan.babylon.api.NetWorkApi
import com.chethan.babylon.db.AppDatabase
import com.chethan.babylon.db.CommentsDao
import com.chethan.babylon.db.PostsDao
import com.chethan.babylon.db.UsersDao
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
        return Room.databaseBuilder(app, AppDatabase::class.java, "Babylon.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUsers(db: AppDatabase): UsersDao {
        return db.usersDao()
    }


    @Singleton
    @Provides
    fun providePosts(db: AppDatabase): PostsDao {
        return db.postsDao()
    }


    @Singleton
    @Provides
    fun provideComments(db: AppDatabase): CommentsDao {
        return db.commentsDao()
    }

}
