package com.rizqanmr.githubusersearch.presentation.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rizqanmr.githubusersearch.data.model.User
import kotlinx.coroutines.Job

interface MainViewModelContract {

    fun getIsLoading(): LiveData<Boolean>

    fun setIsLoading(isLoading: Boolean)

    fun getListUsers(): Job

    fun listUserLiveData(): MutableLiveData<List<User>>

    fun errorListUserLiveData(): LiveData<String>
}