package com.example.tabatatimer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class TimerActivity : AppCompatActivity() {
    var listItems = ArrayList<String>()
    var adapter: ArrayAdapter<String>? = null
    var timer: Sequence? = null
    var phasesPassed: Int = 0
    var timePassed: Int = 0
    var time : CountDownTimer? = null
    var timerArray: Array<CountDownTimer?> = arrayOfNulls(1)
    var isPaused: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        val nameField = findViewById<View>(R.id.timerName) as TextView
        val timeField = findViewById<View>(R.id.timerTime) as TextView
        timer = intent?.getSerializableExtra("timer") as Sequence
        Log.d("timer size", timerArray!!.size.toString())
        var view = findViewById<View>(R.id.timer)
        when (timer!!.color){
            "Синий" -> view.setBackgroundColor(Color.BLUE);
            "Красный" -> view.setBackgroundColor(Color.RED);
            "Зеленый" -> view.setBackgroundColor(Color.GREEN);
        }
        nameField.text = timer!!.name
        val list = findViewById<View>(R.id.timerList) as ListView
        adapter = ArrayAdapter(
                applicationContext,
                R.layout.support_simple_spinner_dropdown_item,
                listItems
        )
        list.adapter = adapter
        for (item in timer!!.phases){
            Log.d("test", item.name)
            listItems.add(item.name + " - " + item.time + "c.");
        }

        timeField.text = ""
        adapter?.notifyDataSetChanged();
        var timerStart = findViewById<View>(R.id.timerStart) as Button

        timerStart.setOnClickListener(View.OnClickListener {
            if (isPaused) {
                isPaused = false
                passService()
                var timerArraySize: Int = timer!!.phases.size - phasesPassed
                timerArray = arrayOfNulls<CountDownTimer>(timerArraySize)
                var i = phasesPassed
                nameField.text = timer!!.phases[i].name
                while (i < timer!!.phases.size) {
                    var counter = i
                    Log.d("create", "creating $i timer")
                    var seconds = when (i) {
                        phasesPassed -> timer!!.phases[i].time - timePassed
                        else -> timer!!.phases[i].time
                    }
                    timerArray[i - phasesPassed] = object : CountDownTimer((seconds * 1000).toLong(), 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            timeField.text = (millisUntilFinished / 1000).toString()
                            timePassed = (timer!!.phases[counter].time - (millisUntilFinished / 1000)).toInt()
                        }

                        override fun onFinish() {
                            timePassed = 0
                            Log.d("finished from activity", "$counter timer finished")
                            if (counter + 1 < timerArraySize) {
                                phasesPassed += 1
                                nameField.text = timer!!.phases[counter + 1].name
                                timerArray[counter + 1]?.start()
                            } else {
                                phasesPassed = 0
                                nameField.text = timer!!.name
                                timeField.text = ""
                                isPaused = true
                            }
                        }
                    }
                    i += 1
                }
                timerArray[0]?.start()
            }
        })
        var timerStop = findViewById<View>(R.id.timerStop) as Button
        timerStop.setOnClickListener(View.OnClickListener {
            isPaused = true
            for (item in timerArray)
                item?.cancel()
            timeField.text = ""
            phasesPassed = 0
            timePassed = 0
            nameField.text = timer!!.name.toString()
        })
        var timerPause = findViewById<View>(R.id.timerPause) as Button
        timerPause.setOnClickListener(View.OnClickListener {
            isPaused = true
            for (item in timerArray)
                item?.cancel()
        })
        var timerSkip = findViewById<View>(R.id.timerSkip) as Button
        timerSkip.setOnClickListener(View.OnClickListener {
            for (item in timerArray)
                item?.cancel()
            phasesPassed += 1
            timePassed = 0
            if (phasesPassed < timer!!.phases.size) {
                isPaused = false
                var timerArraySize: Int = timer!!.phases.size - phasesPassed
                timerArray = arrayOfNulls<CountDownTimer>(timerArraySize)
                var i = phasesPassed
                nameField.text = timer!!.phases[i].name
                while (i < timer!!.phases.size) {
                    var counter = i
                    Log.d("create", "creating $i timer")
                    var seconds = when (i) {
                        phasesPassed -> timer!!.phases[i].time - timePassed
                        else -> timer!!.phases[i].time
                    }
                    timerArray[i - phasesPassed] = object : CountDownTimer((seconds * 1000).toLong(), 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            timeField.text = (millisUntilFinished / 1000).toString()
                            timePassed = (timer!!.phases[counter].time - (millisUntilFinished / 1000)).toInt()
                        }

                        override fun onFinish() {
                            timePassed = 0
                            Log.d("finished", "$counter timer finished")
                            if (counter + 1 < timerArraySize) {
                                phasesPassed += 1
                                nameField.text = timer!!.phases[counter + 1].name
                                timerArray[counter + 1]?.start()
                            } else {
                                phasesPassed = 0
                                nameField.text = timer!!.name
                                timeField.text = ""
                                isPaused = true
                            }
                        }
                    }
                    i += 1
                }
                timerArray[0]?.start()
            } else {
                isPaused = true
                for (item in timerArray)
                    item?.cancel()
                timeField.text = ""
                phasesPassed = 0
                timePassed = 0
                nameField.text = timer!!.name.toString()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        for (item in timerArray)
            item?.cancel()
        stopService(Intent(this, TimerService::class.java))
        finish()
    }

    fun passService(){
        var data = Intent(this, TimerService::class.java)
        data.putExtra("timer", timer)
        data.putExtra("timePassed", timePassed)
        data.putExtra("phasesPassed", phasesPassed)
        data.putExtra("isPaused", isPaused)
        startService(data)
    }
}
