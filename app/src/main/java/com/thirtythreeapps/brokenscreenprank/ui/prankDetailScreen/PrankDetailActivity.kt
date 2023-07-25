package com.thirtythreeapps.brokenscreenprank.ui.prankDetailScreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment.STYLE_NO_TITLE
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.thirtythreeapps.brokenscreenprank.R
import com.thirtythreeapps.brokenscreenprank.databinding.ActivityPrankDetailBinding
import com.thirtythreeapps.brokenscreenprank.ui.chooseEffect.EffectModel
import com.thirtythreeapps.brokenscreenprank.ui.chooseEffect.effectFromJson
import com.thirtythreeapps.brokenscreenprank.ui.commen.FloatingWindowService
import com.thirtythreeapps.brokenscreenprank.ui.commen.PrankType


class PrankDetailActivity : AppCompatActivity() {
    private lateinit var prankModel: EffectModel
    private val TAG ="PrankDetailActivity"
    private lateinit var binding : ActivityPrankDetailBinding
    private val viewModel by viewModels<PrankDetailViewModel> ()
    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted->
            if (isGranted) {
                startPankService()
            }
            else {
                Toast.makeText(this,"Notification permission need",Toast.LENGTH_LONG).show()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrankDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prank  = intent?.getStringExtra(EXTRA_PRANK)
        if (prank==null){
            finish()
        }else{
            prankModel = prank!!.effectFromJson()
        }
        binding.tvToolBarTitle.text =when( prankModel?.prankType){
            PrankType.BROKEN_SCREEN_PRANK->{
                "BROKEN SCREEN"
            }
            else->{
                null
            }

        }
        Log.d(TAG,"effect ${prankModel?.toJson()}")
        Glide.with(this).asBitmap().load(prankModel?.effectPreviewDrawableImage).into(binding.ivPrankPreview)
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

        binding.btStartPrank.setOnClickListener {
            if (Settings.canDrawOverlays(this)){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    checkNotificationPermission()
                }else{
                    startPankService()
                }
            }else{
                showDialog()
            }
        }

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)


    }

    private fun startPankService(){
        val floatingIntent = Intent(this, FloatingWindowService::class.java)
        floatingIntent.action = FloatingWindowService.ACTION_CRACK_PRANK
        floatingIntent.putExtra(FloatingWindowService.EXTRA_PRANK_START_WHEN, viewModel.startPrank.toJson())
        floatingIntent.putExtra(FloatingWindowService.EXTRA_PRANK,this.prankModel.toJson())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(floatingIntent)
        } else {
            startService(floatingIntent)
        }
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun checkNotificationPermission() {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        when {
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
                // make your action here
                startPankService()
            }
            shouldShowRequestPermissionRationale(permission) -> {
                Toast.makeText(this,"Please goto phone settings>app>${getString(R.string.app_name)}>Permission",Toast.LENGTH_LONG).show()
            }
            else -> {
                requestNotificationPermission.launch(permission)
            }
        }
    }
    fun showDialog() {
        val fragmentManager = supportFragmentManager
        val newFragment = EnableOverLayAlertDialog()
        newFragment.setStyle(STYLE_NO_TITLE, R.style.my_dialog)
        newFragment.show(fragmentManager, "dialog")

    }

    companion object{
        val EXTRA_PRANK = "extra_prank"
    }
}