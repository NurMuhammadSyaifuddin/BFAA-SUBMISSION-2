package com.project.githubuser.ui.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.project.githubuser.R
import com.project.githubuser.adapter.UsersAdapter
import com.project.githubuser.databinding.FragmentFollowingBinding
import com.project.githubuser.viewmodel.FollowingViewModel
import com.project.githubuser.ui.detail.DetailActivity
import com.project.githubuser.utils.gone
import com.project.githubuser.utils.visible
import com.project.githubuser.viewmodel.ViewModelFactory

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding as FragmentFollowingBinding
    private lateinit var followingAdapter: UsersAdapter
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // init
        followingAdapter = UsersAdapter()
        followingViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(activity?.application)
        )[FollowingViewModel::class.java]

        getUserFollowing()

    }

    private fun getUserFollowing() {
        showLoading(true)

        binding.apply {
            rvFollowing.adapter = followingAdapter
            rvFollowing.setHasFixedSize(true)
        }

        val username = arguments?.getString(DetailActivity.EXTRA_USER).toString()

        followingViewModel.apply {
            setFollowing(username)
            getFollowing().observe(viewLifecycleOwner) {
                if (it != null) {
                    followingAdapter.users = it
                    showLoading(false)
                } else {
                    showLoading(false)
                    Toast.makeText(activity, getString(R.string.emptyData), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.apply {
                pbFollowing.visible()
                rvFollowing.gone()
            }
        } else {
            binding.apply {
                pbFollowing.gone()
                rvFollowing.visible()
            }

        }
    }

}