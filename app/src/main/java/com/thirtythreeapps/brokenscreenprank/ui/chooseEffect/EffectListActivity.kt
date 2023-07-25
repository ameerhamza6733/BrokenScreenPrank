package com.thirtythreeapps.brokenscreenprank.ui.chooseEffect

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.thirtythreeapps.brokenscreenprank.R
import com.thirtythreeapps.brokenscreenprank.databinding.ActivityEffectListBinding
import com.thirtythreeapps.brokenscreenprank.ui.commen.FloatingWindowService
import com.thirtythreeapps.brokenscreenprank.ui.commen.RateMeDialogFragment
import com.thirtythreeapps.brokenscreenprank.ui.commen.SharedPrefManger
import com.thirtythreeapps.brokenscreenprank.ui.prankDetailScreen.PrankDetailActivity


class EffectListActivity : AppCompatActivity() {
    private val viewModel: EffectListActivityViewModel by viewModels()
    private lateinit var binding: ActivityEffectListBinding
    private var mInterstitialAd: InterstitialAd? = null
    private val TAG = "EffectListActivityTAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEffectListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.adView.loadAd(getAdRequest())
        loadInterstiitalAd()
        binding.layoutToolbar.tvToolBarTitle.text = getString(R.string.crack_effec)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter =
            EffectListAdapter(viewModel.getCrackEffect()) { effectModel ->
                val intent = Intent(this@EffectListActivity, PrankDetailActivity::class.java)
                intent.putExtra(PrankDetailActivity.EXTRA_PRANK, effectModel.toJson())
                startActivity(intent)
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this)
                } else {
                    Log.d(TAG, "The interstitial ad wasn't ready yet.")
                }

            }

        binding.layoutToolbar.toolbarHelpButton.setOnClickListener {

            val fragmentManager = supportFragmentManager
            val newFragment = HelpDialogeFragment()
            newFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.my_dialog)
            newFragment.show(fragmentManager, "HelpDialogeFragment")


        }
        binding.switchTurnOfCrackPrank.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (!isChecked) {
                val floatingIntent = Intent(this, FloatingWindowService::class.java)
                floatingIntent.action = FloatingWindowService.ACTION_STOP_PRANK
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(floatingIntent)
                } else {
                    startService(floatingIntent)
                }

                if (!SharedPrefManger.getFirstPrank(this.applicationContext)){
                    val fragmentManager = supportFragmentManager
                    val newFragment = RateMeDialogFragment()
                    newFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.my_dialog)
                    newFragment.show(fragmentManager, "RateMeDialogFragment")
                    SharedPrefManger.setFirstPrank(this.applicationContext)
                }else{

                }
            }
        }
    }

    private fun loadInterstiitalAd(){
        InterstitialAd.load(this,getString(R.string.admob_intersitial_id), getAdRequest(), object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                    override fun onAdClicked() {
                        // Called when a click is recorded for an ad.
                        Log.d(TAG, "Ad was clicked.")
                    }

                    override fun onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        Log.d(TAG, "Ad dismissed fullscreen content.")
                        mInterstitialAd = null
                        loadInterstiitalAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        // Called when ad fails to show.
                        Log.e(TAG, "Ad failed to show fullscreen content.")
                        mInterstitialAd = null
                    }

                    override fun onAdImpression() {
                        // Called when an impression is recorded for an ad.
                        Log.d(TAG, "Ad recorded an impression.")
                    }

                    override fun onAdShowedFullScreenContent() {
                        // Called when ad is shown.
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }
            }
        })

    }
    private fun getAdRequest(): AdRequest {


       return  AdRequest.Builder().build()

    }

    override fun onResume() {
        super.onResume()
        if (FloatingWindowService.isCrackSecreenPrankRunning) {
            binding.tvTurnOfPrank.visibility = View.VISIBLE
            binding.switchTurnOfCrackPrank.visibility = View.VISIBLE
            binding.switchTurnOfCrackPrank.isChecked = true
        } else {
            binding.tvTurnOfPrank.visibility = View.GONE
            binding.switchTurnOfCrackPrank.visibility = View.GONE
        }

    }
}