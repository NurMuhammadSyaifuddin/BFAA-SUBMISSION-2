package com.project.githubuser.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.githubuser.model.user.User
import com.project.githubuser.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel(private val application: Application) : ViewModel() {

    private val listFollowing = MutableLiveData<MutableList<User>>()

    fun setFollowing(username: String) {
        Repository
            .getFollowing(username)
            .enqueue(object : Callback<MutableList<User>> {
                override fun onResponse(
                    call: Call<MutableList<User>>,
                    response: Response<MutableList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message.toString()}")
                    Toast.makeText(application, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun getFollowing(): LiveData<MutableList<User>> = listFollowing

    companion object {
        private val TAG = FollowingViewModel::class.java.simpleName
    }

}