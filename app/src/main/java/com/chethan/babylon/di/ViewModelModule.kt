package com.chethan.babylon.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chethan.babylon.view.postDetail.UserPostDetailViewModel
import com.chethan.babylon.view.userposts.UserPostViewModel
import com.chethan.babylon.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserPostViewModel::class)
    abstract fun bindUserPostViewModel(userPostViewModel: UserPostViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(UserPostDetailViewModel::class)
    abstract fun bindUserCommentsViewModel(userCommentsViewModel : UserPostDetailViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
