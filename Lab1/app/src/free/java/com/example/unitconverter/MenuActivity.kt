package com.example.unitconverter

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        buttonLenght.setOnClickListener {
            openCalc(0)
        }

        buttonTime.setOnClickListener {
            openCalc(1)
        }

        buttonWeight.setOnClickListener {
            openCalc(2)
        }
    }

    private fun openCalc(choice: Int){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("choice", choice)
        startActivity(intent);
    }
}
