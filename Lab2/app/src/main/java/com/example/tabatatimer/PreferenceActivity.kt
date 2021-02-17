package com.example.tabatatimer

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference.OnPreferenceChangeListener
import android.preference.Preference.OnPreferenceClickListener
import android.preference.PreferenceActivity
import android.preference.PreferenceManager
import android.util.Log
import android.view.WindowManager
import java.io.File
import java.io.FileOutputStream
import java.util.*


class PreferenceActivity : PreferenceActivity() {
    var deleted: Int = 0
    lateinit var locale: Locale
    private var currentLang: String? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)
        val button = findPreference("clear_data")
        button.onPreferenceClickListener =
            OnPreferenceClickListener {
                val outFile: File = File(applicationContext.filesDir, "data.json")
                val out = FileOutputStream(outFile, false)
                val contents: ByteArray = "".toByteArray()
                out.write(contents)
                out.flush()
                out.close()
                deleted = 1
                true
            }
        val listPreference = findPreference("language") as ListPreference
        listPreference.onPreferenceChangeListener =
            OnPreferenceChangeListener { preference, newValue ->
                if (newValue.toString().contains("Russian")){
                    setLocale("ru")
                }
                else if (newValue.toString().contains("Английский")){
                    setLocale("en")
                }
                true
            }
    }

    private fun setLocale(localeName: String) {
        locale = Locale(localeName)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = locale
        res.updateConfiguration(conf, dm)
        finish()
        val intent = Intent(this, com.example.tabatatimer.PreferenceActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        val prefs = PreferenceManager
            .getDefaultSharedPreferences(this)
        val tamount = prefs.getString("text_size", "Средний")
        if (tamount != null) {
            if (tamount.contains("Маленький"))
                adjustFontScale(resources.configuration, 0.5.toFloat());
            else if (tamount.contains("Большой"))
                adjustFontScale(resources.configuration, 2.0.toFloat());
            else if (tamount.contains("Средний"))
                adjustFontScale(resources.configuration, 1.0.toFloat());
        };
        val data = Intent()
        data.putExtra("deleted", deleted)
        setResult(6, data)
        finish()
        super.onBackPressed()
    }


    fun adjustFontScale(configuration: Configuration, scale: Float) {
        configuration.fontScale = scale
        val metrics = resources.displayMetrics
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)
    }
}