package com.project.githubuser.utils

import androidx.recyclerview.widget.DiffUtil
import com.project.githubuser.model.repos.Repos

class ReposDivCallback(
    private val oldReposList: List<Repos>,
    private val newReposList: List<Repos>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldReposList.size

    override fun getNewListSize(): Int = newReposList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldReposList[oldItemPosition].htmlUrl == newReposList[newItemPosition].htmlUrl

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldReposList[oldItemPosition].htmlUrl == newReposList[newItemPosition].htmlUrl

}