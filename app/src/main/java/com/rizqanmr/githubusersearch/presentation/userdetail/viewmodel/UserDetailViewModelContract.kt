package com.rizqanmr.githubusersearch.presentation.userdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rizqanmr.githubusersearch.data.network.models.UserDetailNetwork
import kotlinx.coroutines.Job

interface UserDetailViewModelContract {

    fun getIsLoading(): LiveData<Boolean>

    fun setIsLoading(isLoading: Boolean)

    fun getUserDetail(username: String): Job

    fun userDetailLiveData(): MutableLiveData<UserDetailNetwork>

    fun errorUserDetailLiveData(): LiveData<String>
}