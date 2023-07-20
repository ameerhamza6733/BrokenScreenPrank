package com.thirtythreeapps.brokenscreenprank.ui.chooseEffect

import com.google.gson.Gson
import com.thirtythreeapps.brokenscreenprank.ui.commen.PrankType

data class EffectModel (val effectDrawableImage:Int,val prankType:Int=PrankType.BROKEN_SCREEN_PRANK){
    fun toJson():String {
        return Gson().toJson(this)
    }


}

fun String.effectFromJson():EffectModel{
    return Gson().fromJson<EffectModel>(this,EffectModel::class.java)
}

