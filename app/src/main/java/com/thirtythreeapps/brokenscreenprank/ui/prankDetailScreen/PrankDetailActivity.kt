package com.thirtythreeapps.brokenscreenprank.ui.prankDetailScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.thirtythreeapps.brokenscreenprank.databinding.ActivityPrankDetailBinding
import com.thirtythreeapps.brokenscreenprank.ui.chooseEffect.effectFromJson
import com.thirtythreeapps.brokenscreenprank.ui.commen.PrankType

class PrankDetailActivity : AppCompatActivity() {
    private val TAG ="PrankDetailActivity"
    private lateinit var binding : ActivityPrankDetailBinding
    private val viewModel by viewModels<PrankDetailViewModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrankDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prank  = intent?.getStringExtra(EXTRA_PRANK)
        val effectModel =  prank?.effectFromJson()
        binding.tvToolBarTitle.text =when( effectModel?.prankType){
            PrankType.BROKEN_SCREEN_PRANK->{
                "BROKEN SCREEN"
            }
            else->{
                null
            }

        }
        Log.d(TAG,"effect ${effectModel?.toJson()}")
        Glide.with(this).asBitmap().load(effectModel?.effectDrawableImage).into(binding.ivPrankPreview)
       viewModel.shakeAndTouchRadioLiveData.observe(this){startPrank->
           when(startPrank.startWhen){
               StartPrank.START_PRANK_WHEN_TOUCH->{
                   binding.btTouchActive.visibility =  View.VISIBLE
                   binding.btTouchInActive.visibility = View.GONE
                   binding.btShakeInActive.visibility = View.VISIBLE
                   binding.btShakeActive.visibility = View.GONE
               }
               StartPrank.START_PRANK_WHEN_SHAKE->{
                   binding.btTouchActive.visibility = View.GONE
                   binding.btTouchInActive.visibility = View.VISIBLE
                   binding.btShakeActive.visibility = View.VISIBLE
                   binding.btShakeInActive.visibility = View.GONE
               }
           }
       }
        binding.btBack.setOnClickListener {
            finish()
        }
        binding.btTouchInActive.setOnClickListener {
            viewModel.startAndTouchRadioClick(StartPrank(StartPrank.START_PRANK_WHEN_TOUCH))
        }
        binding.btShakeInActive.setOnClickListener {
            viewModel.startAndTouchRadioClick(StartPrank(StartPrank.START_PRANK_WHEN_SHAKE))
        }

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

    }

    companion object{
        val EXTRA_PRANK = "extra_prank"
    }
}