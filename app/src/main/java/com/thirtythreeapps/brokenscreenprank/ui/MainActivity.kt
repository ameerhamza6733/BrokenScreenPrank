package com.thirtythreeapps.brokenscreenprank.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.thirtythreeapps.brokenscreenprank.R
import com.thirtythreeapps.brokenscreenprank.databinding.ActivityMainBinding
import com.thirtythreeapps.brokenscreenprank.ui.chooseEffect.HelpDialogeFragment
import com.thirtythreeapps.brokenscreenprank.ui.commen.RateMeDialogFragment
import com.thirtythreeapps.brokenscreenprank.ui.home.HomeActivity

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

        val fragmentManager = supportFragmentManager
        val newFragment = RateMeDialogFragment()
        newFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.my_dialog)
        newFragment.show(fragmentManager, "HelpDialogeFragment")
    }
}