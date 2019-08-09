package com.chethan.babylon.view.userposts

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
import com.chethan.babylon.AppExecutors
import com.chethan.babylon.R
import com.chethan.babylon.api.binding.FragmentDataBindingComponent
import com.chethan.babylon.common.UserPostListAdapter
import com.chethan.babylon.databinding.UserPostFragmentBinding
import com.chethan.babylon.di.Injectable
import com.chethan.babylon.testing.OpenForTesting
import com.chethan.babylon.utils.autoCleared
import javax.inject.Inject

/**
 * Created by Chethan on 7/30/2019.
 */

@OpenForTesting
class UserPostFragment : Fragment(), Injectable {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<UserPostFragmentBinding>()

    private var adapter by autoCleared<UserPostListAdapter>()

    private lateinit var userPostViewModel: UserPostViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.user_post_fragment,
            container,
            false,
            dataBindingComponent
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userPostViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(UserPostViewModel::class.java)
        binding.setLifecycleOwner(viewLifecycleOwner)
        initRecyclerView()
        val rvAdapter = UserPostListAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors
        ) { post ->
            navController().navigate(
                UserPostFragmentDirections.showDetailsScreen(post.id.toString(), post.title.toString())
            )
        }


        binding.postList.adapter = rvAdapter
        adapter = rvAdapter

    }


    private fun initRecyclerView() {

        binding.userPost = userPostViewModel.userPosts
        userPostViewModel.userPosts.observe(viewLifecycleOwner, Observer { result ->
            adapter.submitList(result?.data)

        })


    }

    fun navController() = findNavController()

}