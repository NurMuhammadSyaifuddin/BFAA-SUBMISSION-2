package com.project.githubuser.model.repos

import com.google.gson.annotations.SerializedName

data class Repos(
    @SerializedName("html_url")
    val htmlUrl: String,
    val description: String,
)
