package com.broken.screen.crack.prank.crackedscreen.ui.prankDetailScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PrankDetailViewModel : ViewModel() {

     lateinit var startPrank: StartPrank
    private val _shakeOrTouchRadioMutableLiveData: MutableLiveData<StartPrank> = MutableLiveData()
    val shakeAndTouchRadioLiveData: LiveData<StartPrank> = _shakeOrTouchRadioMutableLiveData

    init {
        startAndTouchRadioClick(StartPrank(StartPrank.START_PRANK_WHEN_TOUCH))
    }

    fun startAndTouchRadioClick(startPrank: StartPrank) {
        this.startPrank = startPrank
        _shakeOrTouchRadioMutableLiveData.value = startPrank
    }
}