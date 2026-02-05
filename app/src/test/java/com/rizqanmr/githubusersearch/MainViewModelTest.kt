package com.rizqanmr.githubusersearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rizqanmr.githubusersearch.data.model.User
import com.rizqanmr.githubusersearch.data.network.Result
import com.rizqanmr.githubusersearch.domain.repository.UserRepository
import com.rizqanmr.githubusersearch.presentation.main.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: UserRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        repository = mockk()
        viewModel = MainViewModel(repository)
    }

    // =========================
    // TEST SUCCESS
    // =========================
    @Test
    fun `getListUsers success should update list and loading`() = runTest {

        // Given
        val dummyUsers = listOf(
            User(id = 1, username = "John", avatarUrl = "https://avatar.iran.liara.run/public/23"),
            User(id = 2, username = "Jane", avatarUrl = "https://avatar.iran.liara.run/public/2")
        )

        coEvery {
            repository.getListUser()
        } returns com.rizqanmr.githubusersearch.data.network.Result.Success(dummyUsers)

        // When
        val job = viewModel.getListUsers()
        job.join()

        // Then
        assertEquals(false, viewModel.getIsLoading().value)
        assertEquals(dummyUsers, viewModel.listUserLiveData().value)
    }

    // =========================
    // TEST ERROR
    // =========================
    @Test
    fun `getListUsers error should update errorLiveData`() = runTest {

        // Given
        val errorMessage = "Network Error"

        coEvery {
            repository.getListUser()
        } returns com.rizqanmr.githubusersearch.data.network.Result.Error(Exception(errorMessage))

        // When
        val job = viewModel.getListUsers()
        job.join()

        // Then
        assertEquals(
            errorMessage,
            viewModel.errorListUserLiveData().value
        )

        assertEquals(false, viewModel.getIsLoading().value)
    }

    // =========================
    // LOADING
    // =========================
    @Test
    fun `loading should be true while fetching data`() = runTest {

        coEvery {
            repository.getListUser()
        } coAnswers {
            delay(1000)
            Result.Success(mockk())
        }

        // When
        viewModel.getListUsers()

        runCurrent()

        // Then → masih loading
        assertEquals(true, viewModel.getIsLoading().value)

        // Selesaikan coroutine
        advanceUntilIdle()

        // After done
        assertEquals(false, viewModel.getIsLoading().value)
    }

    // =========================
    // SET LOADING
    // =========================
    @Test
    fun `setIsLoading should update loading state`() {

        // When
        viewModel.setIsLoading(true)

        // Then
        assertEquals(true, viewModel.getIsLoading().value)
    }
}