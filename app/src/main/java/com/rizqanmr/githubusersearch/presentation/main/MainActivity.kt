package com.rizqanmr.githubusersearch.presentation.main

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rizqanmr.githubusersearch.R
import com.rizqanmr.githubusersearch.data.Constant
import com.rizqanmr.githubusersearch.data.model.User
import com.rizqanmr.githubusersearch.databinding.ActivityMainBinding
import com.rizqanmr.githubusersearch.databinding.ItemUserBinding
import com.rizqanmr.githubusersearch.presentation.main.viewmodel.MainViewModel
import com.rizqanmr.githubusersearch.presentation.userdetail.UserDetailActivity
import com.rizqanmr.githubusersearch.presentation.usersearchresult.UserSearchResultActivity
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.menu_search)?.actionView as SearchView
        val component = ComponentName(this, UserSearchResultActivity::class.java)
        val searchableInfo = searchManager.getSearchableInfo(component)
        searchView.setSearchableInfo(searchableInfo)

        return true
    }

    private fun setupRecyclerView() {
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }

    private fun selectedUser() {
        userAdapter.setUserListener(object : UserAdapter.UserListener {
            override fun onItemClick(itemUserBinding: ItemUserBinding, user: User?) {
                UserDetailActivity.newIntent(this@MainActivity, bundleOf().apply {
                    putString(Constant.EXTRA_USERNAME, user?.username)
                })
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