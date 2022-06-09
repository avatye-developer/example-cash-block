package com.avatye.sdk.example.roulette

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.avatye.cashblock.CashBlockSDK
import com.avatye.cashblock.base.component.domain.entity.user.GenderType
import com.avatye.cashblock.base.component.domain.entity.user.Profile
import com.avatye.cashblock.feature.roulette.CashBlockRoulette
import com.avatye.cashblock.feature.roulette.component.model.listener.ITicketCount
import com.avatye.sdk.example.roulette.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val vb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CashBlockSDK.sessionStart(context = this)
        setContentView(vb.root)
        /** set profile */
        vb.buttonAuth.setOnClickListener {
            if (vb.userId.text.isNullOrEmpty()) {
                Toast.makeText(this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                setAppUserID()
            }
        }
        vb.buttonCondition.setOnClickListener {
            ticketCondition()
        }
        vb.buttonEnter.setOnClickListener {
            if (vb.wrapProfile.isVisible) {
                CashBlockRoulette.launch(context = this@MainActivity)
            }
        }
    }

    override fun onResume() {
        viewAppUserID()
        super.onResume()
    }

    override fun onDestroy() {
        CashBlockSDK.sessionEnd(context = this)
        super.onDestroy()
    }

    private fun viewAppUserID() {
        val appUserID = CashBlockSDK.getUserProfile(this@MainActivity).userId
        if (appUserID.isEmpty()) {
            vb.wrapAuth.visibility = View.VISIBLE
            vb.wrapProfile.visibility = View.GONE
        } else {
            vb.wrapAuth.visibility = View.GONE
            vb.wrapProfile.visibility = View.VISIBLE
            vb.profileId.text = "ID : $appUserID"
        }
    }

    private fun ticketCondition() {
        CashBlockRoulette.checkTicketCondition(context = this, listener = object : ITicketCount {
            override fun callback(balance: Int, condition: Int) {
                Log.d("PARTNER", "TicketCondition { balance: $balance, condition: $condition }")
                vb.ticketBalance.text = "Ticket Balance: $balance"
                vb.ticketCondition.text = "Ticket Condition: $condition"
            }
        })
    }

    private fun setAppUserID() {
        val appUserID = vb.userId.text.toString()
        CashBlockSDK.setUserProfile(
            context = this,
            profile = Profile(
                userId = appUserID,
                birthYear = 2000,
                gender = GenderType.MALE
            )
        )
        viewAppUserID()
    }

}