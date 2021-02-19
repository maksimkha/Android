package com.example.tabatatimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

class PhaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phase)
        val phase = intent?.getSerializableExtra("phase") as Phase
        Log.d("phase is ", phase.name + "  " + phase.time)
        val nameField = findViewById<View>(R.id.phaseName) as EditText
        nameField.setText(phase.name)
        val timeField = findViewById<View>(R.id.phaseTime) as EditText
        timeField.setText(phase.time.toString())
        val saveBtn = findViewById<View>(R.id.saveButton) as Button
        saveBtn.setOnClickListener(View.OnClickListener {
            phase.name = nameField.text.toString()
            phase.time = timeField.text.toString().toInt()
            val data = Intent()
            data.putExtra("phase", phase)
            setResult(RESULT_OK, data)
            finish()
        })
    }
}