package com.thirtythreeapps.brokenscreenprank.ui.commen

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.thirtythreeapps.brokenscreenprank.R
import com.thirtythreeapps.brokenscreenprank.ui.chooseEffect.EffectListActivity
import com.thirtythreeapps.brokenscreenprank.ui.chooseEffect.PrankDetail
import com.thirtythreeapps.brokenscreenprank.ui.chooseEffect.prankDetailFromJson
import com.thirtythreeapps.brokenscreenprank.ui.prankDetailScreen.StartPrank


class FloatingWindowService : Service(), View.OnTouchListener {
    val TAG = "FloatingUiMangerBaseTAG"
    private lateinit var context: Context
    private lateinit var windowManager: WindowManager
    private var floatingView: View? = null
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    private var effectModel: PrankDetail? = null

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        create(context)
        createForegroundNotification()
        screenWidth = application.resources.displayMetrics.widthPixels
        screenHeight = application.resources.displayMetrics.heightPixels

    }

    private fun create(context: Context) {
        this.context = context
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }


    private fun showFloatingWindow(layout: Int, params: WindowManager.LayoutParams) {
        floatingView?.let {
            windowManager.removeView(it)
        }
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        floatingView = inflater.inflate(layout, null)
        params!!.gravity = Gravity.TOP or Gravity.START
        windowManager.addView(floatingView, params)

        floatingView!!.setOnClickListener {
            showFloatingWindow(
                R.layout.floating_layout,
                getNonClickAbleLayoutParams(width = screenWidth, height = screenHeight)
            )
            val crackImage = floatingView?.findViewById<ImageView>(R.id.ivCrackEffect)
            crackImage?.visibility = View.VISIBLE
            Glide.with(this).asBitmap().load(effectModel?.effectDrawableImage).into(crackImage!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isCrackSecreenPrankRunning = false
        if (floatingView != null)
            windowManager.removeView(floatingView)
    }

    private fun getNonClickAbleLayoutParams(width: Int, height: Int): WindowManager.LayoutParams {

        val flags = (WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                or WindowManager.LayoutParams.FLAG_LAYOUT_IN_OVERSCAN)

        return WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
            },
            flags,
            PixelFormat.TRANSLUCENT
        )
    }

    private fun getClickAbleLayoutParams(width: Int, height: Int): WindowManager.LayoutParams {
        return WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
            },
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        )
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start the service in the foreground, which requires a notification

        intent?.getStringExtra(EXTRA_PRANK)?.let { prankJson ->
            effectModel = prankJson.prankDetailFromJson()
        }
        when (intent?.action) {
            ACTION_CRACK_PRANK -> {
                isCrackSecreenPrankRunning = true
            }

            ACTION_STOP_PRANK -> {
                isCrackSecreenPrankRunning = false
                stopSelf()
                stopSelf(1)
            }
        }
        intent?.getStringExtra(EXTRA_PRANK_START_WHEN)?.let { startPrankJson ->
            val startPrank = Gson().fromJson<StartPrank>(startPrankJson, StartPrank::class.java)
            when (startPrank.startWhen) {
                StartPrank.START_PRANK_WHEN_TOUCH -> {

                    showFloatingWindow(
                        R.layout.floating_layout,
                        getClickAbleLayoutParams(screenWidth, screenHeight)
                    )
                }

                StartPrank.START_PRANK_WHEN_SHAKE -> {
                    applicationContext.vibrateDevice(200)
                    showFloatingWindow(
                        R.layout.floating_layout,
                        getNonClickAbleLayoutParams(width = screenWidth, height = screenHeight)
                    )
                    val crackImage = floatingView?.findViewById<ImageView>(R.id.ivCrackEffect)
                    crackImage?.visibility = View.VISIBLE
                    Glide.with(this).asBitmap().load(effectModel?.effectDrawableImage)
                        .into(crackImage!!)
                }

                else -> {}
            }
        }

        return START_STICKY
    }

    private fun createForegroundNotification() {
        // Create a notification channel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "ForegroundServiceChannel"
            val channel = NotificationChannel(
                channelId,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager?.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(context, EffectListActivity::class.java)
        var contentIntent: PendingIntent? = PendingIntent.getActivity(
            context,
            0, notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )        // Create the notification to show the foreground service
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, "ForegroundServiceChannel")
                .setContentTitle(applicationContext.getString(R.string.app_name))
                .setContentIntent(contentIntent)
                .setContentText("Click to remove the effect")
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)

        // Start the service in the foreground with the notification
        startForeground(22, notificationBuilder.build())
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        Log.d(TAG, "ontouch")

        return false
    }

    companion object {
        val EXTRA_PRANK_START_WHEN = "prank_start_When"
        val EXTRA_PRANK = "extra_prank"
        val ACTION_STOP_PRANK = "action_stop_prank"
        val ACTION_CRACK_PRANK: String = "action_crack_prank"
        var isCrackSecreenPrankRunning = false
    }
}