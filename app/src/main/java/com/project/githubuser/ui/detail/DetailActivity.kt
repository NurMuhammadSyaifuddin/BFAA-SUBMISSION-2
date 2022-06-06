package com.project.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.project.githubuser.R
import com.project.githubuser.adapter.SectionPagerAdapter
import com.project.githubuser.databinding.ActivityDetailBinding
import com.project.githubuser.viewmodel.DetailUserViewModel
import com.project.githubuser.model.user.User
import com.project.githubuser.utils.loadImage
import com.project.githubuser.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailUserViewModel: DetailUserViewModel
    private lateinit var sectionPagerAdapter: SectionPagerAdapter
    private var bundle: Bundle? = null

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init
        bundle = Bundle()
        detailUserViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this.application)
        )[DetailUserViewModel::class.java]
        sectionPagerAdapter = SectionPagerAdapter(this, bundle)

        getDataUser()

        onAction()

        sendBundle()
        getPages()

    }

    private fun getPages() {
        binding.apply {
            vpDetail.adapter = sectionPagerAdapter

            TabLayoutMediator(tabLayout, vpDetail) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    private fun sendBundle() {
        user = intent?.getParcelableExtra<User>(EXTRA_USER) as User
        bundle?.putString(EXTRA_USER, user.username)
    }

    private fun onAction() {

        binding.apply {

            imgClose.setOnClickListener { finish() }

        }

    }

    private fun getDataUser() {

        intent?.let {
            user = it.getParcelableExtra<User>(EXTRA_USER) as User

            val username = user.username

            detailUserViewModel.setDetailUser(username)
            getDetailUser()

        }
    }

    private fun getDetailUser() {

        detailUserViewModel.getDetailUser().observe(this) {
            if (it != null) {
                binding.apply {
                    avatarUser.loadImage(it.avatar)
                    tvUsername.text = it.username
                    tvName.text = it.name
                    tvCompany.text = it.company
                    tvLocation.text = it.location
                    tvRepository.text = it.repository.toString()
                    tvFollowers.text = it.followers.toString()
                    tvFollowing.text = it.following.toString()
                }
            }
        }

    }

    companion object {
        const val EXTRA_USER = "extra_user"
        private val TAB_TITLES = intArrayOf(R.string.tab1, R.string.tab2, R.string.tab3)
    }

}