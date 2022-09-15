package com.avatye.sdk.example.adcash.banner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.avatye.sdk.example.adcash.PlacementParcel
import com.avatye.cashblock.domain.support.extension.extraParcel
import com.avatye.cashblock.domain.support.extension.launch
import com.avatye.cashblock.unit.adcash.AdError
import com.avatye.cashblock.unit.adcash.BannerAdSize
import com.avatye.cashblock.unit.adcash.view.BannerAdView
import com.avatye.sdk.example.adcash.databinding.ActivityBannerWidget300x250Binding

class BannerWidget300X250Activity : AppCompatActivity() {

    companion object {
        fun open(activity: Activity, parcel: PlacementParcel) {
            activity.launch(
                intent = Intent(activity, BannerWidget300X250Activity::class.java).apply {
                    putExtra(PlacementParcel.NAME, parcel)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            )
        }
    }

    private val pid: String by lazy {
        extraParcel<PlacementParcel>(PlacementParcel.NAME)?.pid ?: ""
    }

    private val vb: ActivityBannerWidget300x250Binding by lazy {
        ActivityBannerWidget300x250Binding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        vb.pid.text = pid
        vb.textFormWidget.text = "...."
        vb.bannerAdView.setBannerAdSize(BannerAdSize.W320XH50)
        vb.bannerAdView.setPlacementId(placementId = pid)
        vb.bannerAdView.listener = object : BannerAdView.Listener {
            override fun onLoaded() {
                vb.textFormWidget.text = "onLoaded"
            }

            override fun onFailed(adError: AdError) {
                vb.textFormWidget.text = "onFailed { adError: $adError }"
            }

            override fun onClicked() {
                vb.textFormWidget.text = "onClicked"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        vb.bannerAdView.onResume()
    }

    override fun onPause() {
        super.onPause()
        vb.bannerAdView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        vb.bannerAdView.onDestroy()
    }

}