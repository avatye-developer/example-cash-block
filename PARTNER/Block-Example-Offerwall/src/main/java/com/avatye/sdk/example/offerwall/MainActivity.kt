package com.avatye.sdk.example.offerwall

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.avatye.cashblock.CashBlockSDK
import com.avatye.cashblock.feature.offerwall.CashBlockOfferwall
import com.avatye.sdk.example.offerwall.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val vb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        // session
        CashBlockSDK.sessionStart(context = this@MainActivity)
        // profile
        val profile = CashBlockSDK.getUserProfile(this@MainActivity)
        vb.profileId.text = "ID : ${profile.userId}"
        vb.buttonEnter.setOnClickListener {
            CashBlockOfferwall.launch(context = this@MainActivity)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        CashBlockSDK.sessionEnd(context = this@MainActivity)
    }
}