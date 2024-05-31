package com.rizqanmr.githubusersearch.presentation.userdetail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.rizqanmr.githubusersearch.data.Constant
import com.rizqanmr.githubusersearch.databinding.ActivityUserDetailBinding
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
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(Constant.EXTRA_USERNAME).toString()

        setupToolbar()
        setupViews()
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

    @SuppressLint("PrivateResource")
    private fun setupViews() {
        with(binding) {
            ivAvatar.setCircleImageUrl(
                "https://avatars.githubusercontent.com/u/21984934?v=4",
                com.google.android.material.R.drawable.mtrl_ic_error
            )
            tvName.text = "Rizqan Mubarak Rahman"
            tvUsername.text = username
            tvBio.text = "Android Developer"
            tvCompany.text = "Google Indonesia"
            tvLocation.text = "Jakarta Timur, DKI Jakarta"
            tvBlog.text = "https://rizqanmr.vercel.app/"
            tvEmail.text = "rizqan@gmail.com"
            tvFollowers.text = "102K followers"
            tvFollowing.text = "100 following"
            tvRepositories.text = "Repositories 34"
        }
    }
}