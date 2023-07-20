package com.thirtythreeapps.brokenscreenprank.ui.prankDetailScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thirtythreeapps.brokenscreenprank.R

class PrankDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prank_detail)

        val prank  = intent?.getStringExtra(EXTRA_PRANK)

    }

    companion object{
        val EXTRA_PRANK = "extra_prank"
    }
}