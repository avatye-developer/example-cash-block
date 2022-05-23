package com.avatye.sdk.example.roulette

import android.app.Application
import com.avatye.cashblock.CashBlockSDK

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        CashBlockSDK.initialize(
            application = this,
            appId = "98d4d4c35d594451b21f54718e2bc986",
            appSecret = "c395dbe200ad4493ade96fb92c988fcf1c8df2d3687d49a9ab6f31f7c05e2bf4"
        )
    }
}