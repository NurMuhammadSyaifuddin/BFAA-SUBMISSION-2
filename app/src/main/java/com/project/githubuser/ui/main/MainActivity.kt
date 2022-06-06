package com.project.githubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.project.githubuser.R
import com.project.githubuser.adapter.UsersAdapter
import com.project.githubuser.databinding.ActivityMainBinding
import com.project.githubuser.viewmodel.UserViewModel
import com.project.githubuser.ui.detail.DetailActivity
import com.project.githubuser.utils.*
import com.project.githubuser.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UsersAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init
        userAdapter = UsersAdapter()
        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this.application)
        )[UserViewModel::class.java]


        showEmptyUser(true)

        onAction()

    }

    private fun getDataUsers() {

        showLoading()

        userViewModel.getUsers().observe(this@MainActivity) {

            when {
                it.size == 0 -> {
                    binding.tvNoUsers.visible()
                    hideLoading()
                }
                it != null -> {
                    userAdapter.users = it
                    binding.rvUsers.adapter = userAdapter
                    hideLoading()
                }
                else -> {
                    hideLoading()
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.emptyData),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun onAction() {

        binding.apply {

            userAdapter.onClick { user ->
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USER, user)
                    startActivity(it)
                }
            }

            edtSearchMain.addTextChangedListener {

                rvUsers.adapter = null

                val result = edtSearchMain.text
                if (!(result.isNullOrEmpty())) {
                    userViewModel.setUsers(result.toString().trim())
                    getDataUsers()
                    showEmptyUser(false)

                } else {
                    showEmptyUser(true)
                    binding.rvUsers.gone()
                }
            }

            edtSearchMain.setOnEditorActionListener { _, actionId, _ ->

                val dataSearch = edtSearchMain.text.toString().trim()

                if (actionId == EditorInfo.IME_ACTION_SEARCH && dataSearch.isNotEmpty()) {
                    userViewModel.setUsers(dataSearch)
                    getDataUsers()
                    showEmptyUser(false)

                    hideSoftKeyboard(this@MainActivity, binding.root)

                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false

            }

        }

    }

    private fun showLoading() {
        binding.pbMain.visible()
    }

    private fun hideLoading() {
        binding.pbMain.gone()
    }

    private fun showEmptyUser(state: Boolean) {
        binding.apply {
            if (state) {
                rvUsers.gone()
                imgNoData.visible()
                tvNoData.visible()
            } else {
                rvUsers.visible()
                imgNoData.gone()
                tvNoData.gone()
                tvNoUsers.gone()
            }
        }
    }

}