package com.avatye.sdk.example.adcash.banner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.avatye.sdk.example.adcash.PlacementParcel
import com.avatye.cashblock.domain.support.extension.extraParcel
import com.avatye.cashblock.domain.support.extension.launch
import com.avatye.cashblock.unit.adcash.AdError
import com.avatye.cashblock.unit.adcash.BannerAdSize
import com.avatye.cashblock.unit.adcash.loader.BannerAdLoader
import com.avatye.sdk.example.adcash.databinding.ActivityBanner320x100Binding

class Banner320X100Activity : AppCompatActivity() {

    companion object {
        fun open(activity: Activity, parcel: PlacementParcel) {
            activity.launch(
                intent = Intent(activity, Banner320X100Activity::class.java).apply {
                    putExtra(PlacementParcel.NAME, parcel)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
            )
        }
    }

    private val pid: String by lazy {
        extraParcel<PlacementParcel>(PlacementParcel.NAME)?.pid ?: ""
    }

    private val vb: ActivityBanner320x100Binding by lazy {
        ActivityBanner320x100Binding.inflate(LayoutInflater.from(this))
    }

    private var bannerAdLoader: BannerAdLoader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        vb.pid.text = pid
        vb.textFormLoader.text = "...."
        vb.buttonLoad.setOnClickListener {
            it.isEnabled = false
            vb.textFormLoader.text = "...."
            bannerAdLoader = BannerAdLoader(
                ownerActivity = this@Banner320X100Activity,
                placementId = pid,
                bannerAdSize = BannerAdSize.W320XH100,
                listener = object : BannerAdLoader.BannerListener {
                    override fun onLoaded(adView: View, size: BannerAdSize) {
                        vb.viewFormLoader.removeAllViews()
                        vb.viewFormLoader.addView(adView)
                        vb.textFormLoader.text = "onLoaded"
                        Toast.makeText(this@Banner320X100Activity, "Loader::onLoaded", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailed(error: AdError) {
                        it.isEnabled = true
                        vb.textFormLoader.text = "onFailed(${error.errorMessage})"
                        Toast.makeText(this@Banner320X100Activity, "onFailed::${error.errorMessage}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onClicked() {
                        Toast.makeText(this@Banner320X100Activity, "onClicked", Toast.LENGTH_SHORT).show()
                    }
                }
            )
            bannerAdLoader?.requestAd()
        }
    }

    override fun onResume() {
        super.onResume()
        bannerAdLoader?.onResume()
    }

    override fun onPause() {
        super.onPause()
        bannerAdLoader?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        bannerAdLoader?.onDestroy()
    }

}