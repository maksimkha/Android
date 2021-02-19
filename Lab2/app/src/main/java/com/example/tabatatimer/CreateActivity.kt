package com.example.tabatatimer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size


class CreateActivity : AppCompatActivity() {
    private val phaseList = ArrayList<Phase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        val editName = findViewById<View>(R.id.editName) as EditText
        val editTime = findViewById<View>(R.id.editTime) as EditText
        val addBtn = findViewById<View>(R.id.add_button) as Button
        val delBtn = findViewById<View>(R.id.delete_button) as Button
        val list = findViewById<View>(R.id.list) as ListView
        val arrayList = ArrayList<String>()
        val adapter = ArrayAdapter<String>(
            applicationContext,
            R.layout.support_simple_spinner_dropdown_item,
            arrayList
        )

        list.adapter = adapter

        addBtn.setOnClickListener(View.OnClickListener {
            val phase = Phase(
                name = editName.text.toString(),
                time = editTime.text.toString().toInt()
            )
            phaseList.add(phase)
            arrayList.add(phase.name + " - " + phase.time)
            editName.text = null
            editTime.text = null
            adapter.notifyDataSetChanged()
        })

        delBtn.setOnClickListener(View.OnClickListener {
            if (arrayList.size > 0) {
                phaseList.removeLast()
                arrayList.removeLast()
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.createmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val list = findViewById<View>(R.id.list) as ListView
        if (list.size > 0) {
            val timerName = findViewById<View>(R.id.seqName) as EditText
            val colorSpinner = findViewById<View>(R.id.colorSpin) as Spinner
            val timer = Sequence(timerName.text.toString(), colorSpinner.selectedItem.toString(), phaseList)
            val data = Intent()
            data.putExtra("timer", timer)
            setResult(RESULT_OK, data)
            finish()
        }
        return true
    }

}
