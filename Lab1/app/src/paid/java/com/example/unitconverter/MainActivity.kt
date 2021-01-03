package com.example.unitconverter

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), ButtonsFragment.ButtonClicked {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val strings = when (intent.getIntExtra("choice", 0)){
            0 -> resources.getStringArray(R.array.length_array)
            1 -> resources.getStringArray(R.array.time_array)
            2 -> resources.getStringArray(R.array.weight_array)
            else -> {
                resources.getStringArray(R.array.length_array)
            }
        }
        val spinnerUpper: Spinner = findViewById<Spinner>(R.id.fromBox)
        val spinnerBot: Spinner = findViewById<Spinner>(R.id.toBox)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, strings
        )
        spinnerUpper.adapter = adapter
        spinnerBot.adapter = adapter
    }


    override fun onDestroy() {
        super.onDestroy()
        val spinnerUpper: Spinner = findViewById<Spinner>(R.id.fromBox)
        val spinnerBot: Spinner = findViewById<Spinner>(R.id.toBox)
        spinnerUpper.adapter = null
        spinnerBot.adapter = null
    }

    override fun sendChar(text: String?) {
        val frag: ConvertFragment? = supportFragmentManager.findFragmentById(R.id.inputFragment) as ConvertFragment?
        frag?.updateText(text)
    }

    override fun clearChar() {
        val frag: ConvertFragment? = supportFragmentManager.findFragmentById(R.id.inputFragment) as ConvertFragment?
        frag?.clearCharText()
    }

    override fun clearAll() {
        val frag: ConvertFragment? = supportFragmentManager.findFragmentById(R.id.inputFragment) as ConvertFragment?
        frag?.clearText()
    }

    override fun convert() {
        val frag: ConvertFragment? = supportFragmentManager.findFragmentById(R.id.inputFragment) as ConvertFragment?
        val kef = intent.getIntExtra("choice", 0)
        frag?.convert(kef)
    }

    override fun swap() {
        val frag: ConvertFragment? = supportFragmentManager.findFragmentById(R.id.inputFragment) as ConvertFragment?
        frag?.swapText()
    }
}


