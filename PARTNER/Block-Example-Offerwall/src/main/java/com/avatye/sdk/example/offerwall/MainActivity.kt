package com.avatye.sdk.example.offerwall

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.avatye.cashblock.CashBlockSDK
import com.avatye.cashblock.base.component.domain.entity.user.GenderType
import com.avatye.cashblock.base.component.domain.entity.user.Profile
import com.avatye.cashblock.feature.offerwall.CashBlockOfferwall
import com.avatye.sdk.example.offerwall.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val vb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        /** set profile */
        vb.buttonAuth.setOnClickListener {
            if (vb.userId.text.isNullOrEmpty()) {
                Toast.makeText(this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                setAppUserID()
            }
        }
        vb.buttonEnter.setOnClickListener {
            if (vb.wrapProfile.isVisible) {
                CashBlockOfferwall.launch(context = this@MainActivity)
            }
        }
    }

    override fun onResume() {
        viewAppUserID()
        super.onResume()
    }

    private fun viewAppUserID() {
        val appUserID = CashBlockSDK.getUserProfile(this@MainActivity).userId
        if (appUserID.isEmpty()) {
            vb.wrapAuth.isVisible = true
            vb.wrapProfile.isVisible = false
        } else {
            vb.wrapAuth.isVisible = false
            vb.wrapProfile.isVisible = true
            vb.profileId.text = "ID : $appUserID"
        }
    }

    private fun setAppUserID() {
        val appUserID = vb.userId.text.toString()
        CashBlockSDK.setUserProfile(
            context = this,
            profile = Profile(userId = appUserID, birthYear = 2000, gender = GenderType.MALE)
        )
        viewAppUserID()
    }

}