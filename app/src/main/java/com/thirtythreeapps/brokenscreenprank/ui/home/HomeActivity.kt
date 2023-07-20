package com.thirtythreeapps.brokenscreenprank.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.thirtythreeapps.brokenscreenprank.R
import com.thirtythreeapps.brokenscreenprank.databinding.ActivityHomeBinding
import com.thirtythreeapps.brokenscreenprank.ui.chooseEffect.EffectListActivity
import com.thirtythreeapps.brokenscreenprank.ui.commen.PrankType

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recylerViewPrankList.layoutManager = LinearLayoutManager(this)
        binding.recylerViewPrankList.adapter = PrankAdupter(createPrankList()){
            startActivity(Intent(this@HomeActivity,EffectListActivity::class.java))
        }

    }

    private fun createPrankList():List<PrankModel>{
        return listOf<PrankModel>(
            PrankModel(getString(R.string.broken_scre),
                PrankType.BROKEN_SCREEN_PRANK
            )
        )
    }
}