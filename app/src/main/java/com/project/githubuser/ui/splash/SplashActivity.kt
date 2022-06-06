package com.project.githubuser.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.project.githubuser.R
import com.project.githubuser.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper())
            .postDelayed({
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                }
                finish()
            }, EXTRA_DELAY)
    }

    companion object {
        private const val EXTRA_DELAY = 800L
    }

}