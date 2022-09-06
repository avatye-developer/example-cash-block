package com.avatye.sdk.example.adcash

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.avatye.sdk.example.adcash.interstitial.InterstitialActivity
import com.avatye.sdk.example.adcash.banner.*
import com.avatye.sdk.example.adcash.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val vb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        vb.banner320X50.setOnClickListener {
            Banner320X50Activity.open(activity = this, PlacementParcel(pid = vb.banner320X50Pid.text.toString()))
        }

        vb.bannerWidget320X50.setOnClickListener {
            BannerWidget320X50Activity.open(activity = this, PlacementParcel(pid = vb.bannerWidget320X50Pid.text.toString()))
        }

        vb.banner320X100.setOnClickListener {
            Banner320X100Activity.open(activity = this, PlacementParcel(pid = vb.banner320X100Pid.text.toString()))
        }

        vb.bannerWidget320X100.setOnClickListener {
            BannerWidget320X100Activity.open(activity = this, PlacementParcel(pid = vb.bannerWidget320X100Pid.text.toString()))
        }

        vb.banner300X250.setOnClickListener {
            Banner300X250Activity.open(activity = this, PlacementParcel(pid = vb.banner300X250Pid.text.toString()))
        }

        vb.bannerWidget300X250.setOnClickListener {
            BannerWidget300X250Activity.open(activity = this, PlacementParcel(pid = vb.bannerWidget300X250Pid.text.toString()))
        }

        vb.interstitial.setOnClickListener {
            InterstitialActivity.open(activity = this, PlacementParcel(pid = vb.interstitialPid.text.toString()))
        }
    }
}