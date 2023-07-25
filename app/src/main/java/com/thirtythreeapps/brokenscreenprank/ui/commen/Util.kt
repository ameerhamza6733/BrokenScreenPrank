package com.thirtythreeapps.brokenscreenprank.ui.commen

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat.getSystemService


    fun Context.vibrateDevice(durationMillis: Long) {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Check if the device supports the newer vibration effect API (API level 26+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(durationMillis, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        } else {
            // For older devices, use the deprecated API
            vibrator.vibrate(durationMillis)
        }
    }
