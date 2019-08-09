package com.chethan.babylon.di

import com.chethan.babylon.view.postDetail.UserPostDetailFragment
import com.chethan.babylon.view.userposts.UserPostFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun userPostFragment(): UserPostFragment

    @ContributesAndroidInjector
    abstract fun userPostDetailFragment(): UserPostDetailFragment


}
