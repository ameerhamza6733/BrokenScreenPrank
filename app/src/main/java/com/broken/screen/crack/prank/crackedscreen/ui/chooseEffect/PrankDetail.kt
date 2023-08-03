package com.broken.screen.crack.prank.crackedscreen.ui.chooseEffect

import com.google.gson.Gson
import com.broken.screen.crack.prank.crackedscreen.ui.commen.PrankType

data class PrankDetail (val effectPreviewDrawableImage:Int,val effectDrawableImage:Int, val prankType:Int=PrankType.BROKEN_SCREEN_PRANK){
    fun toJson():String {
        return Gson().toJson(this)
    }


}

fun String.prankDetailFromJson():PrankDetail{
    return Gson().fromJson<PrankDetail>(this,PrankDetail::class.java)
}

