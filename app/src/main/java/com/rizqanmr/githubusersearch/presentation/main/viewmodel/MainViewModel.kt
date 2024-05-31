package com.rizqanmr.githubusersearch.presentation.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqanmr.githubusersearch.data.model.User
import com.rizqanmr.githubusersearch.data.network.Result
import com.rizqanmr.githubusersearch.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserRepository
) : MainViewModelContract, CoroutineScope, ViewModel() {

    private val isLoading = MutableLiveData<Boolean>()
    private val listUserLiveData: MutableLiveData<List<User>> = MutableLiveData()
    private val errorListUserLiveData: MutableLiveData<String> = MutableLiveData()

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

    override fun getIsLoading(): LiveData<Boolean> = isLoading

    override fun setIsLoading(isLoading: Boolean) {
        this.isLoading.value = isLoading
    }

    override fun getListUsers(): Job = viewModelScope.launch {
        setIsLoading(true)

        withContext(Dispatchers.IO) {
            when (val result = repository.getListUser()) {
                is Result.Success -> listUserLiveData.postValue(result.data)
                is Result.Error -> errorListUserLiveData.postValue(result.exception.localizedMessage)
            }
        }

        setIsLoading(false)
    }

    override fun listUserLiveData(): MutableLiveData<List<User>> = listUserLiveData

    override fun errorListUserLiveData(): LiveData<String> = errorListUserLiveData
}