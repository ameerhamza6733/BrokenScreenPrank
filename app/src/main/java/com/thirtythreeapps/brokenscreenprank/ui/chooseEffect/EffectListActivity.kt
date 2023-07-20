package com.thirtythreeapps.brokenscreenprank.ui.chooseEffect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.thirtythreeapps.brokenscreenprank.R
import com.thirtythreeapps.brokenscreenprank.databinding.ActivityEffectListBinding
import com.thirtythreeapps.brokenscreenprank.databinding.RowCrackPreviewBinding
import com.thirtythreeapps.brokenscreenprank.ui.home.PrankModel
import com.thirtythreeapps.brokenscreenprank.ui.prankDetailScreen.PrankDetailActivity

class EffectListActivity : AppCompatActivity() {
    private val viewModel: EffectListActivityViewModel by viewModels()
    private lateinit var binding : ActivityEffectListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEffectListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.layoutToolbar.tvToolBarTitle.text = getString(R.string.crack_effec)
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)
        binding.recyclerView.adapter = EffectListAdapter(viewModel.getCrackEffect()){effectModel->
            Intent(this@EffectListActivity,PrankDetailActivity::class.java).apply {
                putExtra(PrankDetailActivity.EXTRA_PRANK,effectModel.toJson())
            }.let {
                startActivity(it)
            }
        }
    }
}