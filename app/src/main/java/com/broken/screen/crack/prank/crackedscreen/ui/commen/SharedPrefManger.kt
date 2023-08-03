package com.broken.screen.crack.prank.crackedscreen.ui.commen

import android.content.Context
import android.content.SharedPreferences

object SharedPrefManger {
   private const val PREF_NAME = "MyAppPreferences"
   private const val KEY_FIRST_PRANK = "first_prank"
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setFirstPrank(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(KEY_FIRST_PRANK, true)
        editor.apply()
    }

    fun getFirstPrank(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_FIRST_PRANK,false)
    }
}