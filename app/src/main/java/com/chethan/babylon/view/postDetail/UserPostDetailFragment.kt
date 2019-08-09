package com.chethan.babylon.view.postDetail

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
import com.chethan.babylon.AppExecutors
import com.chethan.babylon.R
import com.chethan.babylon.api.binding.FragmentDataBindingComponent
import com.chethan.babylon.common.UserCommentsAdapter
import com.chethan.babylon.databinding.UserDetailFragmentAdapterBinding
import com.chethan.babylon.di.Injectable
import com.chethan.babylon.testing.OpenForTesting
import com.chethan.babylon.utils.autoCleared
import javax.inject.Inject

/**
 * Created by Chethan on 5/3/2019.
 */

@OpenForTesting
class UserPostDetailFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var userPostDetailViewModel: UserPostDetailViewModel
    @Inject
    lateinit var appExecutors: AppExecutors

    var binding by autoCleared<UserDetailFragmentAdapterBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    private var adapter by autoCleared<UserCommentsAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<UserDetailFragmentAdapterBinding>(
            inflater,
            R.layout.user_detail_fragment_adapter,
            container,
            false,
            dataBindingComponent
        )

        binding = dataBinding
        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val rvAdapter = UserCommentsAdapter(
            appExecutors = appExecutors
        )

        binding.commentsList.adapter = rvAdapter
        adapter = rvAdapter
        val params = UserPostDetailFragmentArgs.fromBundle(arguments!!)
        userPostDetailViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(UserPostDetailViewModel::class.java)
        binding.setLifecycleOwner(viewLifecycleOwner)

        userPostDetailViewModel.setPostId(params.postId)
        userPostDetailViewModel.result.observe(viewLifecycleOwner, Observer { result ->
            if (result.data != null)
                adapter.addHeaderAndSubmitList(result!!.data!!)
        })


    }

}