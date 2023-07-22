package com.thirtythreeapps.brokenscreenprank.ui.commen

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.*

 class FloatingUiMangerBase : View.OnTouchListener {
    val TAG = "FloatingUiMangerBaseTAG"
    private var context: Context
    private var windowManager: WindowManager
    open var floatingView: View? = null
    private var params: WindowManager.LayoutParams? = null
    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private var inittalTime = 0L

    protected var draggingHappen = false



    constructor(context: Context) {
        this.context = context
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }



    open fun showFloatingUI(layout:Int, size:Pair<Int,Int>, position:Pair<Int,Int>) {

        val with = size.first * context.resources.displayMetrics.density.toInt()
        val height = size.second * context.resources.displayMetrics.density.toInt()

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        floatingView = inflater.inflate(layout, null)
        Log.d(TAG, "showFloatingUI: floatingView = $floatingView")
        // Define the layout parameters for the floating view
        params = WindowManager.LayoutParams(with, height,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
            }
            ,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        params!!.gravity = Gravity.TOP or Gravity.START
        params!!.x = position.first
        params!!.y = position.second
        enableDrag()
        windowManager.addView(floatingView, params)

    }

    private fun enableDrag(){
        floatingView?.setOnTouchListener(this)
        floatingView?.setOnDragListener { v, event ->
            Log.d(TAG,"drag listerner ${event.action}")
            false
        }

    }

    fun removeUI() {
        if (floatingView != null) {
            windowManager.removeView(floatingView)
            floatingView = null
        }
    }

    fun hind(){
        Log.d(TAG,"hide")
        floatingView?.visibility = View.INVISIBLE
    }

    fun show(){
        Log.d(TAG,"show()")
        floatingView?.visibility = View.VISIBLE
    }

     override fun onTouch(v: View?, event: MotionEvent?): Boolean {
         return false
     }


 }