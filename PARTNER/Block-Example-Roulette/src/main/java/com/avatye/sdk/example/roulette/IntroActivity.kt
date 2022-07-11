package com.avatye.sdk.example.roulette

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.avatye.cashblock.CashBlockSDK
import com.avatye.sdk.example.roulette.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private val vb: ActivityIntroBinding by lazy {
        ActivityIntroBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        Handler(Looper.getMainLooper()).postDelayed({
            val profile = CashBlockSDK.getUserProfile(this@IntroActivity)
            if (profile.userId.isNotEmpty() && profile.birthYear > 0) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 1000L)
    }
}