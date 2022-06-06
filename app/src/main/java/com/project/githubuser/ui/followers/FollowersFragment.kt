package com.project.githubuser.ui.followers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.project.githubuser.R
import com.project.githubuser.adapter.UsersAdapter
import com.project.githubuser.databinding.FragmentFollowersBinding
import com.project.githubuser.viewmodel.FollowersViewModel
import com.project.githubuser.ui.detail.DetailActivity
import com.project.githubuser.utils.gone
import com.project.githubuser.utils.visible
import com.project.githubuser.viewmodel.ViewModelFactory

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding as FragmentFollowersBinding
    private lateinit var followersAdapter: UsersAdapter
    private lateinit var followersViewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // init
        followersAdapter = UsersAdapter()
        followersViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(activity?.application)
        )[FollowersViewModel::class.java]

        getUserFollowers()

    }

    private fun getUserFollowers() {

        showLoading(true)

        binding.apply {
            rvFollowers.adapter = followersAdapter
            rvFollowers.setHasFixedSize(true)
        }

        val username = arguments?.getString(DetailActivity.EXTRA_USER).toString()

        followersViewModel.apply {
            setFollowers(username)
            getFollowers().observe(viewLifecycleOwner) {
                if (it != null) {
                    followersAdapter.users = it
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
        binding.apply {
            if (state) {
                pbFollowers.visible()
                rvFollowers.gone()
            } else {
                pbFollowers.gone()
                rvFollowers.visible()
            }

        }
    }

}