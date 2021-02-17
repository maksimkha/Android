package com.example.tabatatimer

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity


class EditActivity : AppCompatActivity() {
    var listItems = ArrayList<String>()
    var adapter: ArrayAdapter<String>? = null
    var pressed: Int = -1
    var timer: Sequence? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        timer = intent?.getSerializableExtra("timer") as Sequence
        val nameField = findViewById<View>(R.id.seqName) as TextView
        nameField.text = timer!!.name
        val list = findViewById<View>(R.id.list) as ListView
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
        list.setOnItemClickListener { parent, view, position, id ->
            val element = adapter!!.getItemId(position)
            val intent = Intent(this, PhaseActivity::class.java)
            intent.putExtra("phase", timer!!.phases[element.toInt()])
            pressed = element.toInt()
            startActivityForResult(intent, 1)
        }
        var spinner = findViewById<View>(R.id.colorSpin) as Spinner
        when (timer!!.color){
            "Синий" -> spinner.setSelection(0)
            "Красный" -> spinner.setSelection(1)
            "Зеленый" -> spinner.setSelection(2)
        }
        var screen = findViewById<View>(R.id.edit)
        when (timer!!.color){
            "Синий" -> screen.setBackgroundColor(Color.BLUE)
            "Красный" -> screen.setBackgroundColor(Color.RED)
            "Зеленый" -> screen.setBackgroundColor(Color.GREEN)
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("cant be truth")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                timer!!.color = spinner.selectedItem.toString()
                when (timer!!.color){
                    "Синий" -> screen!!.setBackgroundColor(Color.BLUE);
                    "Красный" -> screen!!.setBackgroundColor(Color.RED);
                    "Зеленый" -> screen!!.setBackgroundColor(Color.GREEN);
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.createmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.action_search) {
            val data = Intent()
            data.putExtra("edited timer", timer)
            setResult(1, data)
            finish()
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            var temp = data?.getSerializableExtra("phase") as Phase
            timer?.phases?.set(pressed, temp)
            listItems[pressed] = temp.name + " - " + temp.time + "c."
            adapter?.notifyDataSetChanged();
        }
    }
}