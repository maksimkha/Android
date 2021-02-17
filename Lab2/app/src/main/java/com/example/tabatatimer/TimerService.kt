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
        mMediaPlayer = MediaPlayer.create(this, R.raw.sound)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}

