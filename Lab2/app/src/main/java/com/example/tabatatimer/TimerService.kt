package com.example.tabatatimer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.IBinder
import android.provider.Settings
import android.util.Log


class TimerService : Service() {
    var CountDownTimer = null
    var timerIntent: Sequence? = null
    var timer : CountDownTimer? = null
    var phasesPassed: Int = 0
    var timePassed: Int = 0
    var isPaused: Boolean = true
    var timerArray: Array<CountDownTimer?> = arrayOfNulls(1)
    var mMediaPlayer: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        mMediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        timerIntent = intent.getSerializableExtra("timer") as Sequence
        phasesPassed = intent.getIntExtra("phasesPassed", 0)
        timePassed = intent.getIntExtra("timePassed", 0)
        isPaused = intent.getBooleanExtra("isPaused", true)
        if (!isPaused) {
            var timerArraySize: Int = timerIntent!!.phases.size - phasesPassed
            timerArray = arrayOfNulls<CountDownTimer>(timerArraySize)
            var i = phasesPassed
            while (i < timerIntent!!.phases.size) {
                var counter = i
                Log.d("create", "creating $i timer")
                var seconds = when (i) {
                    phasesPassed -> timerIntent!!.phases[i].time - timePassed
                    else -> timerIntent!!.phases[i].time
                }
                timerArray[i - phasesPassed] = object : CountDownTimer((seconds * 1000).toLong(), 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        timePassed = (timerIntent!!.phases[counter].time - (millisUntilFinished / 1000)).toInt()
                    }
                    override fun onFinish() {
                        timePassed = 0
                        mMediaPlayer!!.start()
                        Log.d("finished from service", "$counter timer finished")
                        if (counter + 1 < timerArraySize) {
                            phasesPassed += 1
                            timerArray[counter + 1]?.start()
                        } else {
                            phasesPassed = 0
                            isPaused = true
                        }
                    }
                }
                i += 1
            }
            timerArray[0]?.start()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        for (item in timerArray)
            item?.cancel()
    }
}

