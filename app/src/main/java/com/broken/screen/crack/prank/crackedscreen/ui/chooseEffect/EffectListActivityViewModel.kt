package com.broken.screen.crack.prank.crackedscreen.ui.chooseEffect

import androidx.lifecycle.ViewModel
import com.broken.screen.crack.prank.crackedscreen.R

class EffectListActivityViewModel() :
    ViewModel() {

    fun getCrackEffect(): List<PrankDetail> {
        return listOf<PrankDetail>(
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_image_1_preview, effectDrawableImage = R.drawable.crack_image_1),
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_image_2_preview, effectDrawableImage = R.drawable.crack_image_2),
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_image_3_preview, effectDrawableImage = R.drawable.crack_image_3),
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_image_4_preview, effectDrawableImage = R.drawable.crack_image_4),
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_image_5_preview, effectDrawableImage = R.drawable.crack_image_5),
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_image_6_preview, effectDrawableImage = R.drawable.crack_image_6),
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_image_7_preview, effectDrawableImage = R.drawable.crack_image_7),
            PrankDetail(effectPreviewDrawableImage = R.drawable.crack_image_8_preview, effectDrawableImage = R.drawable.crack_image_8)
        )
    }
}