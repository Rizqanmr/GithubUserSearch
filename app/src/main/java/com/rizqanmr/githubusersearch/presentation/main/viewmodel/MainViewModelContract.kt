package com.rizqanmr.githubusersearch.presentation.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rizqanmr.githubusersearch.data.network.models.UserNetwork
import kotlinx.coroutines.Job

interface MainViewModelContract {

    fun getIsLoading(): LiveData<Boolean>

    fun setIsLoading(isLoading: Boolean)

    fun getListUsers(): Job

    fun listUserLiveData(): MutableLiveData<List<UserNetwork>>

    fun errorListUserLiveData(): LiveData<String>
}