package com.thirtythreeapps.brokenscreenprank.ui.prankDetailScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.thirtythreeapps.brokenscreenprank.R
import com.thirtythreeapps.brokenscreenprank.databinding.ActivityPrankDetailBinding
import com.thirtythreeapps.brokenscreenprank.ui.chooseEffect.effectFromJson
import com.thirtythreeapps.brokenscreenprank.ui.commen.PrankType

class PrankDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPrankDetailBinding
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

        Glide.with(this).asBitmap().load(effectModel?.effectDrawableImage).into(binding.ivPrankPreview)
        binding.btShakeActive.setOnClickListener {

        }
    }

    companion object{
        val EXTRA_PRANK = "extra_prank"
    }
}