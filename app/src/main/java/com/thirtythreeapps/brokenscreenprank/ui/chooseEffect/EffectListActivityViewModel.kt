package com.thirtythreeapps.brokenscreenprank.ui.chooseEffect

import androidx.lifecycle.ViewModel
import com.thirtythreeapps.brokenscreenprank.R

class EffectListActivityViewModel() :
    ViewModel() {

    fun getCrackEffect(): List<EffectModel> {
        return listOf<EffectModel>(
            EffectModel(effectDrawableImage = R.drawable.first_crack_effect_preview),
            EffectModel(effectDrawableImage = R.drawable.second_crack_effect_preview),
            EffectModel(effectDrawableImage = R.drawable.thired_crack_effect_preview),
            EffectModel(effectDrawableImage = R.drawable.forth_crack_effect_preview),
            EffectModel(effectDrawableImage = R.drawable.fifth_crack_effect_preview)
        )
    }
}