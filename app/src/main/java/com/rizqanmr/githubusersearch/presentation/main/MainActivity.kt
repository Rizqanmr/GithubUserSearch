package com.rizqanmr.githubusersearch.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rizqanmr.githubusersearch.data.models.Users
import com.rizqanmr.githubusersearch.databinding.ActivityMainBinding
import com.rizqanmr.githubusersearch.databinding.ItemUserBinding
import com.rizqanmr.githubusersearch.presentation.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter()
        setupRecyclerView()
        setupObservers()
        selectedUser()
        viewModel.getListUsers()
    }

    private fun setupRecyclerView() {
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }

    private fun selectedUser() {
        userAdapter.setUserListener(object : UserAdapter.UserListener {
            override fun onItemClick(itemUserBinding: ItemUserBinding, user: Users?) {
                Log.d("MYTAG", "${user?.username} clicked")
            }

        })
    }

    private fun setupObservers() {
        viewModel.getIsLoading().observe(this) {
            showLoading(it)
        }
        viewModel.listUserLiveData().observe(this) {
            userAdapter.saveData(it)
        }
        viewModel.errorListUserLiveData().observe(this) {
            Snackbar.make(binding.root, it.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.layoutLoading.progressLoading.isVisible = isLoading
    }
}