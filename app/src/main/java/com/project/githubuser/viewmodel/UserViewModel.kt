package com.project.githubuser.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.project.githubuser.model.user.User
import com.project.githubuser.model.user.UserResponse
import com.project.githubuser.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val application: Application) : ViewModel() {

    private val listUsers = MutableLiveData<MutableList<User>>()

    fun setUsers(query: String?) {
        query?.let {
            Repository
                .getUsers(it)
                .enqueue(object : Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful) {
                            listUsers.postValue(response.body()?.items)
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.message.toString()}")
                        Toast.makeText(application, t.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
        }

    }

    fun getUsers(): LiveData<MutableList<User>> = listUsers

    companion object {
        private val TAG = UserViewModel::class.java.simpleName
    }

}