package com.project.githubuser.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.githubuser.model.detail.DetailUserResponse
import com.project.githubuser.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(private val application: Application) : ViewModel() {

    private val detailUser = MutableLiveData<DetailUserResponse>()

    fun setDetailUser(username: String) {
        Repository
            .getDetailUser(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        detailUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message.toString()}")
                    Toast.makeText(application, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })

    }

    fun getDetailUser(): LiveData<DetailUserResponse> = detailUser

    companion object {
        private val TAG = DetailUserViewModel::class.java.simpleName
    }

}