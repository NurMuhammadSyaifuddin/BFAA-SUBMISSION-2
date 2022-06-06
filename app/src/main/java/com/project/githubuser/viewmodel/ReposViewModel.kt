package com.project.githubuser.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.githubuser.model.repos.Repos
import com.project.githubuser.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReposViewModel(private val application: Application) : ViewModel() {

    private val listRepos = MutableLiveData<MutableList<Repos>>()

    fun setRepos(username: String) {
        Repository
            .getRepos(username)
            .enqueue(object : Callback<MutableList<Repos>> {
                override fun onResponse(
                    call: Call<MutableList<Repos>>,
                    response: Response<MutableList<Repos>>
                ) {
                    if (response.isSuccessful) {
                        listRepos.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<MutableList<Repos>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message.toString()}")
                    Toast.makeText(application, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun getRepos(): LiveData<MutableList<Repos>> = listRepos

    companion object {
        private val TAG = ReposViewModel::class.java.simpleName
    }

}