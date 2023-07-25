package com.thirtythreeapps.brokenscreenprank.ui.chooseEffect

import androidx.lifecycle.ViewModel
import com.thirtythreeapps.brokenscreenprank.R

class EffectListActivityViewModel() :
    ViewModel() {

    fun getCrackEffect(): List<EffectModel> {
        return listOf<EffectModel>(
            EffectModel(effectPreviewDrawableImage = R.drawable.crack_prank_1_preview, effectDrawableImage = R.drawable.crack_effect_1),
            EffectModel(effectPreviewDrawableImage = R.drawable.crack_prank_2_preview, effectDrawableImage = R.drawable.crack_prank_2),
            EffectModel(effectPreviewDrawableImage = R.drawable.crack_prank_3_preview, effectDrawableImage = R.drawable.crack_prank_3),
            EffectModel(effectPreviewDrawableImage = R.drawable.crack_prank_4_preview, effectDrawableImage = R.drawable.crack_prank_4),
            EffectModel(effectPreviewDrawableImage = R.drawable.crack_image_5_preview, effectDrawableImage = R.drawable.crack_image_5),
            EffectModel(effectPreviewDrawableImage = R.drawable.crack_image_6_preview, effectDrawableImage = R.drawable.crack_prank_6),
            EffectModel(effectPreviewDrawableImage = R.drawable.crack_prank_7_preview, effectDrawableImage = R.drawable.crack_image_7),
            EffectModel(effectPreviewDrawableImage = R.drawable.crack_screen_8_preview, effectDrawableImage = R.drawable.crack_prank_8)
        )
    }
}