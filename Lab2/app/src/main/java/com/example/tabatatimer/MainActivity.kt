package com.example.tabatatimer

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {
    var listItems = ArrayList<String>()
    var adapter: ArrayAdapter<String>? = null
    var timerList = ArrayList<Sequence>()
    var pressed: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val list = findViewById<View>(R.id.list) as ListView
        adapter = ArrayAdapter(
            applicationContext,
            R.layout.support_simple_spinner_dropdown_item,
            listItems
        )
        list.adapter = adapter
        var file = File(applicationContext.filesDir, "data.json")
        if(file.exists()) {
            val text: String = file.bufferedReader()
                .use { it.readText(); }
            Log.d("tag", text)
            val gson = Gson()
            val listType = object : TypeToken<List<Sequence>>() {}.type
            try {
                timerList = gson.fromJson(text, listType)
                if (timerList?.size!! > 0) {
                    for (item in timerList!!) {
                        listItems.add(item.name);
                    }
                    adapter?.notifyDataSetChanged();
                }
            }
            catch (e: NullPointerException){
                Log.d("tag", "File is empty")
            }
        }
        else{
            Log.d("tag", "No file")
        }
            list.setOnItemClickListener { parent, view, position, id ->
                val element = adapter!!.getItemId(position)
                val intent = Intent(this, ItemActivity::class.java)
                intent.putExtra("timer", timerList.get(element.toInt()))
                pressed = element.toInt()
                startActivityForResult(intent, 1)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.action_create) {
            val intent = Intent(this, CreateActivity::class.java)
            startActivityForResult(intent, 1)
        }
        if (item.itemId === R.id.action_settings) {
            val intent = Intent(this, PreferenceActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            var timer = data?.getSerializableExtra("timer") as Sequence
            listItems.add(timer.name)
            timerList?.add(timer)
            adapter?.notifyDataSetChanged()
            savetofile()
        }
        else if (resultCode == 4) {
            var temp = data?.getSerializableExtra("timer") as Sequence
            Log.d("delete tag", temp.name)
            listItems.remove(temp.name)
            timerList.removeAt(pressed)
            adapter?.notifyDataSetChanged();
            savetofile()
        }
        else if (resultCode == 5) {
            var temp = data?.getSerializableExtra("timer") as Sequence
            Log.d("edited timer", temp.name)
            timerList[pressed] = temp
            listItems[pressed] = temp.name
            adapter?.notifyDataSetChanged();
            savetofile()
        }
        else if (resultCode == 6) {
            var temp = data?.getSerializableExtra("deleted") as Int
            Log.d("delete  info?", (temp == 1).toString())
            if (temp == 1) {
                timerList.clear()
                listItems.clear()
                adapter?.notifyDataSetChanged();
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val prefs = PreferenceManager
            .getDefaultSharedPreferences(this)
        val regular = prefs.getString("theme", "")
        if (regular != null) {
            if (regular.contains("Дневная"))
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            else if (regular.contains("Ночная"))
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        };

    }

    fun adjustFontScale(configuration: Configuration, scale: Float) {
        configuration.fontScale = scale
        val metrics = resources.displayMetrics
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)
    }

    fun savetofile(){
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
        val jsonTutsListPretty: String = gsonPretty.toJson(timerList)
        val outFile: File = File(applicationContext.filesDir, "data.json")
        Log.d("tag", jsonTutsListPretty)
        val out = FileOutputStream(outFile, false)
        val contents: ByteArray = jsonTutsListPretty.toByteArray()
        out.write(contents)
        out.flush()
        out.close()
    }
}