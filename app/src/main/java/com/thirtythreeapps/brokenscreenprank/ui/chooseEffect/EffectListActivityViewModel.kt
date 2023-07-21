package com.thirtythreeapps.brokenscreenprank.ui.chooseEffect

import androidx.lifecycle.ViewModel
import com.thirtythreeapps.brokenscreenprank.R

class EffectListActivityViewModel() :
    ViewModel() {

    fun getCrackEffect(): List<EffectModel> {
        return listOf<EffectModel>(
            EffectModel(effectDrawableImage = R.drawable.first_crack_effect),
            EffectModel(effectDrawableImage = R.drawable.second_crash_effect_preview),
            EffectModel(effectDrawableImage = R.drawable.thired_crack_effect_preview),
            EffectModel(effectDrawableImage = R.drawable.forth_crack_effect_preview),
            EffectModel(effectDrawableImage = R.drawable.seventh_broken_screen_preview),
            EffectModel(effectDrawableImage = R.drawable.eight_broken_screen_preview)
        )
    }
}