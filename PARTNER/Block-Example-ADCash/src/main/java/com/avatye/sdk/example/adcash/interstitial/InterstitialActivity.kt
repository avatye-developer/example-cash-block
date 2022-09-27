package com.avatye.sdk.example.adcash.interstitial

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.avatye.cashblock.domain.support.extension.extraParcel
import com.avatye.cashblock.domain.support.extension.launch
import com.avatye.cashblock.unit.adcash.AdError
import com.avatye.cashblock.unit.adcash.InterstitialAdType
import com.avatye.cashblock.unit.adcash.loader.InterstitialAdLoader
import com.avatye.sdk.example.adcash.PlacementParcel
import com.avatye.sdk.example.adcash.databinding.ActivityInterstitialBinding

class InterstitialActivity : AppCompatActivity() {

    companion object {
        fun open(activity: Activity, parcel: PlacementParcel) {
            activity.launch(
                intent = Intent(activity, InterstitialActivity::class.java).apply {
                    putExtra(PlacementParcel.NAME, parcel)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            )
        }
    }

    private val pid: String by lazy {
        extraParcel<PlacementParcel>(PlacementParcel.NAME)?.pid ?: ""
    }

    private val vb: ActivityInterstitialBinding by lazy {
        ActivityInterstitialBinding.inflate(LayoutInflater.from(this))
    }

    private var interstitialAdLoader: InterstitialAdLoader? = null
    var index = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        vb.buttonLoad.setOnClickListener {
            index++
            it.isEnabled = false
            vb.pid.text = pid
            vb.interstitialLog.text = "#$index: Request...\n"
            interstitialAdLoader = InterstitialAdLoader(
                ownerActivity = this@InterstitialActivity,
                placementId = pid,
                listener = object : InterstitialAdLoader.InterstitialListener {
                    override fun onLoaded(executor: InterstitialAdLoader.InterstitialExecutor, adType: InterstitialAdType) {
                        vb.interstitialLog.text = vb.interstitialLog.text.toString() + "#$index: onLoaded...\n"
                        executor.show()
                    }

                    override fun onOpened() {
                        vb.interstitialLog.text = vb.interstitialLog.text.toString() + "#$index: onOpened...\n"
                    }

                    override fun onClosed(completed: Boolean) {
                        it.isEnabled = true
                        vb.interstitialLog.text = vb.interstitialLog.text.toString() + "#$index: onClosed { complete: $completed }...\n"
                    }

                    override fun onFailed(error: AdError) {
                        it.isEnabled = true
                        vb.interstitialLog.text = vb.interstitialLog.text.toString() + "#$index: ondFailed: $error\n"
                    }

                    override fun onClicked() {
                        vb.interstitialLog.text = vb.interstitialLog.text.toString() + "#$index: onClicked...\n"
                    }
                }
            )
            interstitialAdLoader?.requestAd()
        }
    }

    override fun onResume() {
        super.onResume()
        interstitialAdLoader?.onResume()
    }

    override fun onPause() {
        super.onPause()
        interstitialAdLoader?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        interstitialAdLoader?.onDestroy()
    }
}