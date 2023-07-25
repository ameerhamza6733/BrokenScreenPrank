package com.thirtythreeapps.brokenscreenprank.ui.prankDetailScreen

import com.google.gson.Gson

class StartPrank(val startWhen:String) {
    companion object{
        val START_PRANK_WHEN_SHAKE ="shake"
        val START_PRANK_WHEN_TOUCH ="touch"
    }
    fun toJson(): String? {
      return  Gson().toJson(this)
    }
}