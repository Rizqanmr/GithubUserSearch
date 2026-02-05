package com.rizqanmr.githubusersearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rizqanmr.githubusersearch.data.network.Result
import com.rizqanmr.githubusersearch.data.network.models.UserDetailNetwork
import com.rizqanmr.githubusersearch.domain.repository.UserRepository
import com.rizqanmr.githubusersearch.presentation.userdetail.viewmodel.UserDetailViewModel
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
class UserDetailViewModelTest {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: UserRepository
    private lateinit var viewModel: UserDetailViewModel

    @Before
    fun setup() {
        repository = mockk()
        viewModel = UserDetailViewModel(repository)
    }

    // =========================
    // SUCCESS CASE
    // =========================
    @Test
    fun `getUserDetail success should update data and loading`() = runTest {

        // Given
        val username = "john"

        val dummyUserDetail = UserDetailNetwork(
            id = 1,
            email = "john@mail.com",
            name = "John Doe",
            avatarUrl = "https://avatar.com/1",
            followers = 100,
            following = 50,
            bio = "Everything is good",
            login = "mojombo",
            blog = "werner",
            htmlUrl = "http://tom.preston-werner.com",
            company = "Google",
            location = "San Francisco",
            publicRepos = 66

        )

        coEvery {
            repository.getUserDetail(username)
        } returns com.rizqanmr.githubusersearch.data.network.Result.Success(dummyUserDetail)

        // When
        val job = viewModel.getUserDetail(username)
        job.join()

        // Then
        assertEquals(false, viewModel.getIsLoading().value)
        assertEquals(dummyUserDetail, viewModel.userDetailLiveData().value)
    }

    // =========================
    // ERROR CASE
    // =========================
    @Test
    fun `getUserDetail error should update errorLiveData`() = runTest {

        // Given
        val username = "john"
        val errorMessage = "User not found"

        coEvery {
            repository.getUserDetail(username)
        } returns com.rizqanmr.githubusersearch.data.network.Result.Error(Exception(errorMessage))

        // When
        val job = viewModel.getUserDetail(username)
        job.join()

        // Then
        assertEquals(errorMessage, viewModel.errorUserDetailLiveData().value)
        assertEquals(false, viewModel.getIsLoading().value)
    }

    // =========================
    // LOADING STATE
    // =========================
    @Test
    fun `loading should be true while fetching data`() = runTest {

        // Given
        val username = "john"

        coEvery {
            repository.getUserDetail(username)
        } coAnswers {
            delay(1000)
            Result.Success(mockk())
        }

        // When
        viewModel.getUserDetail(username)

        runCurrent()

        // Saat masih jalan
        assertEquals(true, viewModel.getIsLoading().value)

        advanceUntilIdle()

        // Setelah selesai
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