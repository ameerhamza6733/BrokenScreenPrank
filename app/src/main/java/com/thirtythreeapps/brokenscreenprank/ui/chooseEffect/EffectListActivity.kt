package com.thirtythreeapps.brokenscreenprank.ui.chooseEffect

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.thirtythreeapps.brokenscreenprank.R
import com.thirtythreeapps.brokenscreenprank.databinding.ActivityEffectListBinding
import com.thirtythreeapps.brokenscreenprank.ui.commen.FloatingWindowService
import com.thirtythreeapps.brokenscreenprank.ui.prankDetailScreen.PrankDetailActivity

class EffectListActivity : AppCompatActivity() {
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

        binding.switchTurnOfCrackPrank.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (!isChecked){
                val floatingIntent = Intent(this, FloatingWindowService::class.java)
                floatingIntent.action = FloatingWindowService.ACTION_STOP_PRANK
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(floatingIntent)
                } else {
                    startService(floatingIntent)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
            if (FloatingWindowService.isCrackSecreenPrankRunning){
                binding.tvTurnOfPrank.visibility = View.VISIBLE
                binding.switchTurnOfCrackPrank.visibility = View.VISIBLE
                binding.switchTurnOfCrackPrank.isChecked = true
            }else{
                binding.tvTurnOfPrank.visibility= View.GONE
                binding.switchTurnOfCrackPrank.visibility = View.GONE
            }

    }
}