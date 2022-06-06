package com.project.githubuser.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.githubuser.model.user.User
import com.project.githubuser.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel(private val application: Application) : ViewModel() {

    private val listFollowers = MutableLiveData<MutableList<User>>()

    fun setFollowers(username: String) {
        Repository
            .getFollowers(username)
            .enqueue(object : Callback<MutableList<User>> {
                override fun onResponse(
                    call: Call<MutableList<User>>,
                    response: Response<MutableList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<MutableList<User>>, t: Throwable) {
                    Toast.makeText(application, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun getFollowers(): LiveData<MutableList<User>> = listFollowers

}