package com.project.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(application) as T
            modelClass.isAssignableFrom(DetailUserViewModel::class.java) -> DetailUserViewModel(
                application
            ) as T
            modelClass.isAssignableFrom(ReposViewModel::class.java) -> ReposViewModel(application) as T
            modelClass.isAssignableFrom(FollowersViewModel::class.java) -> FollowersViewModel(
                application
            ) as T
            modelClass.isAssignableFrom(FollowingViewModel::class.java) -> FollowingViewModel(
                application
            ) as T
            else -> throw IllegalArgumentException("unkno model class ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application?): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                val instance = ViewModelFactory(application as Application)
                INSTANCE = instance
                instance
            }
    }

}