package com.thirtythreeapps.brokenscreenprank.ui.chooseEffect

import androidx.lifecycle.ViewModel
import com.thirtythreeapps.brokenscreenprank.R

class EffectListActivityViewModel() :
    ViewModel() {

    fun getCrackEffect(): List<PrankDetail> {
        return listOf<PrankDetail>(
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_image_1_preview, effectDrawableImage = R.drawable.crack_effect_1),
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_prank_2_preview, effectDrawableImage = R.drawable.crack_prank_2),
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_prank_3_preview, effectDrawableImage = R.drawable.crack_prank_3),
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_prank_4_preview, effectDrawableImage = R.drawable.crack_prank_4),
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_image_5_preview, effectDrawableImage = R.drawable.crack_image_5),
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_image_6_preview, effectDrawableImage = R.drawable.crack_prank_6),
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_prank_7_preview, effectDrawableImage = R.drawable.crack_image_7),
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_screen_8_preview, effectDrawableImage = R.drawable.crack_prank_8)
        )
    }
}