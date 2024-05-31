package com.rizqanmr.githubusersearch.presentation.usersearchresult.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rizqanmr.githubusersearch.data.network.models.UserNetwork
import kotlinx.coroutines.Job

interface UserSearchResultViewModelContract {

    fun getIsLoading(): LiveData<Boolean>

    fun setIsLoading(isLoading: Boolean)

    fun searchUsers(query: String): Job

    fun listUserLiveData(): MutableLiveData<List<UserNetwork>>

    fun errorListUserLiveData(): LiveData<String>
}