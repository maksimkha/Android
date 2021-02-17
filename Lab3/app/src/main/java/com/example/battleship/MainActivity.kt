package com.example.battleship

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        var emailField = findViewById<View>(R.id.textEmail) as EditText
        var passField = findViewById<View>(R.id.textPassword) as EditText
        var errorField = findViewById<View>(R.id.textError) as TextView
        var entBtn = findViewById<View>(R.id.buttonEnt) as Button
        var regBtn = findViewById<View>(R.id.buttonReg) as Button
        regBtn.setOnClickListener{
            if (emailField.text.isEmpty() || passField.text.isEmpty()){
                errorField.visibility = View.VISIBLE
            }
            else{
                errorField.visibility = View.INVISIBLE
                auth.createUserWithEmailAndPassword(
                    emailField.text.toString(),
                    passField.text.toString()).addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        Log.i("result", "u have registred")
                        startActivity(Intent(this, MenuActivity::class.java))
                    }
                    else{
                        Log.i("result", "error occured")
                    }
                }
            }
        }
        entBtn.setOnClickListener{
            if (emailField.text.isEmpty() || passField.text.isEmpty()){
                errorField.visibility = View.VISIBLE
            }
            else{
                errorField.visibility = View.INVISIBLE
                auth.signInWithEmailAndPassword(
                    emailField.text.toString(),
                    passField.text.toString()).addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        Log.i("result", "u have signed")
                        startActivity(Intent(this, MenuActivity::class.java))
                    }
                    else{
                        Log.i("result", "error occurred")
                    }
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MenuActivity::class.java))
        } else {
            Log.i("signing","need to sign in first")
        }
    }
}