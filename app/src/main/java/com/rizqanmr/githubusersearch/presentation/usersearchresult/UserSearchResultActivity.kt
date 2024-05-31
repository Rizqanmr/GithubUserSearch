package com.rizqanmr.githubusersearch.presentation.usersearchresult

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.rizqanmr.githubusersearch.databinding.ActivityUserSearchResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserSearchResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserSearchResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleIntent(intent)
        setupToolbar()
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
            binding.tvResult.text = "Search query was: $query"
        }
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

}