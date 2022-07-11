package com.avatye.sdk.example.roulette

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.avatye.cashblock.CashBlockSDK
import com.avatye.cashblock.feature.roulette.CashBlockRoulette
import com.avatye.cashblock.feature.roulette.component.model.listener.ITicketCount
import com.avatye.sdk.example.roulette.databinding.ActivityMainBinding

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
        vb.buttonCondition.setOnClickListener {
            CashBlockRoulette.checkTicketCondition(context = this, listener = object : ITicketCount {
                override fun callback(balance: Int, condition: Int) {
                    Log.d("PARTNER", "TicketCondition { balance: $balance, condition: $condition }")
                    vb.ticketBalance.text = "Ticket Balance: $balance"
                    vb.ticketCondition.text = "Ticket Condition: $condition"
                }
            })
        }
        vb.buttonEnter.setOnClickListener {
            CashBlockRoulette.launch(context = this@MainActivity)
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