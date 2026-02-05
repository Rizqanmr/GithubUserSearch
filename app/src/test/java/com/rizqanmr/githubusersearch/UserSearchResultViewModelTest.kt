package com.rizqanmr.githubusersearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rizqanmr.githubusersearch.data.model.User
import com.rizqanmr.githubusersearch.data.network.Result
import com.rizqanmr.githubusersearch.domain.repository.UserRepository
import com.rizqanmr.githubusersearch.presentation.usersearchresult.viewmodel.UserSearchResultViewModel
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
class UserSearchResultViewModelTest {

    // Untuk LiveData
    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    // Untuk Coroutine
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: UserRepository
    private lateinit var viewModel: UserSearchResultViewModel

    @Before
    fun setup() {
        repository = mockk()
        viewModel = UserSearchResultViewModel(repository)
    }

    // =========================
    // SUCCESS
    // =========================
    @Test
    fun `searchUsers success should update list and loading`() = runTest {

        // Given
        val query = "john"

        val dummyUsers = listOf(
            User(1, "John", "url1"),
            User(2, "Johnny", "url2")
        )

        coEvery {
            repository.searchUsers(query)
        } returns com.rizqanmr.githubusersearch.data.network.Result.Success(dummyUsers)

        // When
        val job = viewModel.searchUsers(query)

        job.join()

        // Then
        assertEquals(false, viewModel.getIsLoading().value)
        assertEquals(dummyUsers, viewModel.listUserLiveData().value)
    }

    // =========================
    // ERROR
    // =========================
    @Test
    fun `searchUsers error should update errorLiveData`() = runTest {

        // Given
        val query = "john"
        val errorMessage = "Network Error"

        coEvery {
            repository.searchUsers(query)
        } returns Result.Error(Exception(errorMessage))

        // When
        val job = viewModel.searchUsers(query)
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

        // Given
        val query = "john"

        coEvery {
            repository.searchUsers(query)
        } coAnswers {
            delay(1000)
            Result.Success(mockk())
        }

        // When
        viewModel.searchUsers(query)

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