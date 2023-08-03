package com.broken.screen.crack.prank.crackedscreen.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.broken.screen.crack.prank.crackedscreen.R
import com.broken.screen.crack.prank.crackedscreen.databinding.ActivityMainBinding
import com.broken.screen.crack.prank.crackedscreen.ui.home.HomeActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MobileAds.initialize(this) {}
        Glide.with(this).load(R.drawable.bg_image_first_screen).into(binding.image);
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.btStart.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

    }
}