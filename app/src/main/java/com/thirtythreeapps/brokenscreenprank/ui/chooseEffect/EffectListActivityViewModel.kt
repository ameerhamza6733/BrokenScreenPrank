package com.thirtythreeapps.brokenscreenprank.ui.chooseEffect

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class EffectListActivityViewModel(application: Application) : AndroidViewModel(application = application) {
val context = application.applicationContext

    fun getCrackEffect():List<EffectModel>{
        return listOf<EffectModel>(EffectModel(effectDrawableImage = context.getDrawable()))
    }
}