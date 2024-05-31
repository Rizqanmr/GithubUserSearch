package com.rizqanmr.githubusersearch.presentation.usersearchresult

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rizqanmr.githubusersearch.data.Constant
import com.rizqanmr.githubusersearch.data.model.User
import com.rizqanmr.githubusersearch.databinding.ActivityUserSearchResultBinding
import com.rizqanmr.githubusersearch.databinding.ItemUserBinding
import com.rizqanmr.githubusersearch.presentation.main.UserAdapter
import com.rizqanmr.githubusersearch.presentation.userdetail.UserDetailActivity
import com.rizqanmr.githubusersearch.presentation.usersearchresult.viewmodel.UserSearchResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSearchResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserSearchResultBinding
    private lateinit var userAdapter: UserAdapter
    private val viewModel: UserSearchResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleIntent(intent)
        userAdapter = UserAdapter()
        setupToolbar()
        setupRecyclerView()
        selectedUser()
        setupObservers()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            if (query != null) {
                viewModel.searchUsers(query)
            }
        }
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupRecyclerView() {
        binding.rvUserResults.apply {
            layoutManager = LinearLayoutManager(this@UserSearchResultActivity)
            adapter = userAdapter
        }
    }

    private fun selectedUser() {
        userAdapter.setUserListener(object : UserAdapter.UserListener {
            override fun onItemClick(itemUserBinding: ItemUserBinding, user: User?) {
                UserDetailActivity.newIntent(this@UserSearchResultActivity, bundleOf().apply {
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