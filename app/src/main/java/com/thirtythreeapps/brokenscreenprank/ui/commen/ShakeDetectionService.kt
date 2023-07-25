package com.thirtythreeapps.brokenscreenprank.ui.commen

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.thirtythreeapps.brokenscreenprank.R
import com.thirtythreeapps.brokenscreenprank.ui.chooseEffect.EffectListActivity
import com.thirtythreeapps.brokenscreenprank.ui.chooseEffect.PrankDetail
import com.thirtythreeapps.brokenscreenprank.ui.chooseEffect.prankDetailFromJson
import com.thirtythreeapps.brokenscreenprank.ui.home.PrankModel
import com.thirtythreeapps.brokenscreenprank.ui.prankDetailScreen.StartPrank

class ShakeDetectionService : Service() {

    private lateinit var prankModel : PrankDetail
    private lateinit var startPrank: StartPrank

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometerSensor: Sensor

    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private var lastUpdate = 0L
    private val SHAKE_THRESHOLD = 800

    private val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val currentTime = System.currentTimeMillis()
                if ((currentTime - lastUpdate) > 100) {
                    val timeDifference = currentTime - lastUpdate
                    lastUpdate = currentTime

                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    val acceleration =
                        Math.abs(x + y + z - lastX - lastY - lastZ) / timeDifference * 10000

                    if (acceleration > SHAKE_THRESHOLD) {
                      if (!FloatingWindowService.isCrackSecreenPrankRunning){
                          applicationContext.vibrateDevice(200)
                          val floatingIntent = Intent(applicationContext, FloatingWindowService::class.java)
                          floatingIntent.action = FloatingWindowService.ACTION_CRACK_PRANK
                          floatingIntent.putExtra(FloatingWindowService.EXTRA_PRANK_START_WHEN,startPrank.toJson() )
                          floatingIntent.putExtra(FloatingWindowService.EXTRA_PRANK,prankModel.toJson())
                          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                              startForegroundService(floatingIntent)
                          } else {
                              startService(floatingIntent)
                          }
                          stopSelf()
                      }
                    }

                    lastX = x
                    lastY = y
                    lastZ = z
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // Not used in this example
        }
    }

    override fun onCreate() {
        super.onCreate()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.getStringExtra(EXTRA_PRANK)?.let {extraPrank->
            prankModel = extraPrank.prankDetailFromJson()
        }
        intent?.getStringExtra(EXTRA_PRANK_START_WHEN)?.let {
            startPrank = Gson().fromJson(it,StartPrank::class.java)
        }
        when(intent?.action){
            ACTION_STOP->{
                if (FloatingWindowService.isCrackSecreenPrankRunning){
                    val stopPrank = Intent(this,FloatingWindowService::class.java)
                    stopPrank.action = FloatingWindowService.ACTION_STOP_PRANK
                    ContextCompat.startForegroundService(applicationContext,stopPrank)
                }
                stopSelf()
            }
        }
        return START_STICKY
    }

    private fun startForegroundService() {
        // Build a notification for the foreground service
        val notification = createNotification()

        // Start the foreground service
        startForeground(NOTIFICATION_ID, notification)

        // Register the sensor listener
        sensorManager.registerListener(
            sensorListener,
            accelerometerSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    private fun createNotification(): Notification {
        val notificationIntent = Intent(applicationContext, EffectListActivity::class.java)
        var contentIntent: PendingIntent? = PendingIntent.getActivity(
            applicationContext,
            0, notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, "ForegroundServiceChannel")
                .setContentTitle(applicationContext.getString(R.string.app_name))
                .setContentIntent(contentIntent)
                .setContentText("Shake device")
                .setSmallIcon(R.drawable.baseline_waving_hand_24)
        return notificationBuilder.build()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        sensorManager.unregisterListener(sensorListener)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        val ACTION_STOP ="action_stop"
        val EXTRA_PRANK = "extra_prank"
        val EXTRA_PRANK_START_WHEN ="EXTRA_PRANK_START_WHEN"
        private const val NOTIFICATION_ID = 12345
    }
}
