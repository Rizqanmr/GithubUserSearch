package com.rizqanmr.githubusersearch.presentation.userdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqanmr.githubusersearch.data.network.Result
import com.rizqanmr.githubusersearch.data.network.models.UserDetailNetwork
import com.rizqanmr.githubusersearch.domain.repository.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: RemoteDataSource
) : UserDetailViewModelContract, CoroutineScope, ViewModel() {

    private val isLoading = MutableLiveData<Boolean>()
    private val userDetailLiveData: MutableLiveData<UserDetailNetwork> = MutableLiveData()
    private val errorUserDetailLiveData: MutableLiveData<String> = MutableLiveData()

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

    override fun getIsLoading(): LiveData<Boolean> = isLoading

    override fun setIsLoading(isLoading: Boolean) {
        this.isLoading.value = isLoading
    }

    override fun getUserDetail(username: String): Job = viewModelScope.launch {
        setIsLoading(true)

        when (val result = repository.getUserDetail(username)) {
            is Result.Success -> userDetailLiveData.value = result.data
            is Result.Error -> errorUserDetailLiveData.value = result.errorBody.toString()
        }

        setIsLoading(false)
    }

    override fun userDetailLiveData(): MutableLiveData<UserDetailNetwork> = userDetailLiveData

    override fun errorUserDetailLiveData(): LiveData<String> = errorUserDetailLiveData

}