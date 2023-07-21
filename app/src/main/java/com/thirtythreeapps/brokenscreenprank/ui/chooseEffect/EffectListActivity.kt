package com.thirtythreeapps.brokenscreenprank.ui.chooseEffect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.thirtythreeapps.brokenscreenprank.R
import com.thirtythreeapps.brokenscreenprank.databinding.ActivityEffectListBinding
import com.thirtythreeapps.brokenscreenprank.ui.prankDetailScreen.PrankDetailActivity

class EffectListActivity : AppCompatActivity() {
    private val TAG ="EffectListActivity"
    private val viewModel: EffectListActivityViewModel by viewModels()
    private lateinit var binding: ActivityEffectListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEffectListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.layoutToolbar.tvToolBarTitle.text = getString(R.string.crack_effec)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter =
            EffectListAdapter(viewModel.getCrackEffect()) { effectModel ->

                val intent = Intent(this@EffectListActivity, PrankDetailActivity::class.java)
                intent.putExtra(PrankDetailActivity.EXTRA_PRANK, effectModel.toJson())
                startActivity(intent)
            }
    }
}