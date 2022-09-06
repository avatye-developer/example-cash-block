package com.avatye.sdk.example.roulette

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.avatye.cashblock.CashBlockSDK
import com.avatye.cashblock.base.component.entity.user.Profile
import com.avatye.cashblock.business.model.specify.GenderType
import com.avatye.sdk.example.roulette.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val vb: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        vb.buttonAuth.setOnClickListener {
            if (vb.userId.text.isNullOrEmpty()) {
                Toast.makeText(this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                val appUserID = vb.userId.text.toString()
                CashBlockSDK.setUserProfile(
                    context = this,
                    profile = Profile(
                        userId = appUserID,
                        birthYear = 2000,
                        gender = GenderType.MALE
                    )
                )
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}