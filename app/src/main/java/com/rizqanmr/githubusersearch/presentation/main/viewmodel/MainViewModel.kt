package com.rizqanmr.githubusersearch.presentation.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqanmr.githubusersearch.data.Result
import com.rizqanmr.githubusersearch.data.models.Users
import com.rizqanmr.githubusersearch.domain.repository.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RemoteDataSource
) : MainViewModelContract, CoroutineScope, ViewModel() {

    private val isLoading = MutableLiveData<Boolean>()
    private val listUserLiveData: MutableLiveData<List<Users>> = MutableLiveData()
    private val errorListUserLiveData: MutableLiveData<String> = MutableLiveData()

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

    override fun getIsLoading(): LiveData<Boolean> = isLoading

    override fun setIsLoading(isLoading: Boolean) {
        this.isLoading.value = isLoading
    }

    override fun getListUsers(): Job = viewModelScope.launch {
        setIsLoading(true)

        when (val result = repository.getListUser()) {
            is Result.Success -> listUserLiveData.value = result.data
            is Result.Error -> errorListUserLiveData.value = result.errorBody.toString()
        }

        setIsLoading(false)
    }

    override fun listUserLiveData(): MutableLiveData<List<Users>> = listUserLiveData

    override fun errorListUserLiveData(): LiveData<String> = errorListUserLiveData
}