package com.rizqanmr.githubusersearch.presentation.usersearchresult.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqanmr.githubusersearch.data.network.Result
import com.rizqanmr.githubusersearch.data.network.models.UserNetwork
import com.rizqanmr.githubusersearch.domain.repository.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class UserSearchResultViewModel @Inject constructor(
    private val repository: RemoteDataSource
) : UserSearchResultViewModelContract, CoroutineScope, ViewModel() {

    private val isLoading = MutableLiveData<Boolean>()
    private val listUserLiveData: MutableLiveData<List<UserNetwork>> = MutableLiveData()
    private val errorListUserLiveData: MutableLiveData<String> = MutableLiveData()

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

    override fun getIsLoading(): LiveData<Boolean> = isLoading

    override fun setIsLoading(isLoading: Boolean) {
        this.isLoading.value = isLoading
    }

    override fun searchUsers(query: String): Job = viewModelScope.launch {
        setIsLoading(true)

        when (val result = repository.searchUsers(query)) {
            is Result.Success -> listUserLiveData.value = result.data.items
            is Result.Error -> errorListUserLiveData.value = result.errorBody.toString()
        }

        setIsLoading(false)
    }

    override fun listUserLiveData(): MutableLiveData<List<UserNetwork>> = listUserLiveData

    override fun errorListUserLiveData(): LiveData<String> = errorListUserLiveData
}