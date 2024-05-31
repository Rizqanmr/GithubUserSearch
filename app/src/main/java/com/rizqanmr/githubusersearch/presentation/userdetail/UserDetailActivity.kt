package com.rizqanmr.githubusersearch.presentation.userdetail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.rizqanmr.githubusersearch.data.Constant
import com.rizqanmr.githubusersearch.data.models.UserDetail
import com.rizqanmr.githubusersearch.databinding.ActivityUserDetailBinding
import com.rizqanmr.githubusersearch.presentation.userdetail.viewmodel.UserDetailViewModel
import com.rizqanmr.githubusersearch.utils.setCircleImageUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    companion object {
        fun newIntent(activity: Activity, bundle: Bundle?) {
            activity.startActivity(
                Intent(activity, UserDetailActivity::class.java).apply {
                    bundle?.let { putExtras(it) }
                }
            )
        }
    }

    private lateinit var binding: ActivityUserDetailBinding
    private val viewModel: UserDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupObservers()
        viewModel.getUserDetail(intent.getStringExtra(Constant.EXTRA_USERNAME).toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("PrivateResource")
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)

            val upArrow = ContextCompat.getDrawable(
                this@UserDetailActivity,
                com.google.android.material.R.drawable.ic_arrow_back_black_24
            )
            upArrow?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            setHomeAsUpIndicator(upArrow)
        }
    }

    private fun setupObservers() {
        viewModel.getIsLoading().observe(this) {
            showLoading(it)
        }
        viewModel.userDetailLiveData().observe(this) {
            mappingUserDetail(it)
        }
        viewModel.errorUserDetailLiveData().observe(this) {
            Snackbar.make(binding.root, it.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("PrivateResource")
    private fun mappingUserDetail(userDetail: UserDetail) {
        with(binding) {
            data = userDetail
            ivAvatar.setCircleImageUrl(
                userDetail.avatarUrl,
                com.google.android.material.R.drawable.mtrl_ic_error
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.layoutLoading.progressLoading.isVisible = isLoading
    }
}