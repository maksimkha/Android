package com.example.tabatatimer

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ItemActivity : AppCompatActivity() {
    var listItems = ArrayList<String>()
    var adapter: ArrayAdapter<String>? = null
    var timer: Sequence? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        val nameField = findViewById<View>(R.id.textView) as TextView
        timer = intent?.getSerializableExtra("timer") as Sequence
        var view = findViewById<View>(R.id.timerName)
        Log.d("item color", timer!!.color)
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
        adapter?.notifyDataSetChanged();
        var timerBtn = findViewById<View>(R.id.startButton) as Button
        timerBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, TimerActivity::class.java)
            intent.putExtra("timer", timer)
            startActivity(intent)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.itemmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.action_delete) {
            val data = Intent()
            data.putExtra("timer", timer)
            setResult(4, data)
            finish()
        }
        else if (item.itemId === R.id.action_edit){
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("timer", timer)
            startActivityForResult(intent, 1)
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1) {
            timer = data?.getSerializableExtra("edited timer") as Sequence
            listItems.clear()
            for (item in timer!!.phases){
                Log.d("test", item.name)
                listItems.add(item.name + " - " + item.time + "c.");
            }
            var view = findViewById<View>(R.id.timerName)
            Log.d("item color", timer!!.color)
            when (timer!!.color){
                "Синий" -> view.setBackgroundColor(Color.BLUE);
                "Красный" -> view.setBackgroundColor(Color.RED);
                "Зеленый" -> view.setBackgroundColor(Color.GREEN);
            }
            adapter?.notifyDataSetChanged();
        }
    }

    override fun onBackPressed() {
        val data = Intent()
        data.putExtra("timer", timer)
        setResult(5, data)
        finish()
        super.onBackPressed()
    }
}