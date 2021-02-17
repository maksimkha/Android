package com.example.battleship

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class MenuActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private var database: FirebaseDatabase? = null
    var avatarId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        val currentUser = auth.currentUser
        var curUsUID = currentUser?.uid
        val avatarIdRef = database!!.getReference("custom_users/$curUsUID/avatarId")
        avatarIdRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.i("trying to read info", dataSnapshot.value.toString())
                    avatarId = dataSnapshot.value as Long
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("trying to read info", "error with database reading")
            }
        })
        var intent = Intent(this, AccountActivity::class.java)
        var crtBtn = findViewById<View>(R.id.buttonCreate) as Button
        var fndBtn = findViewById<View>(R.id.buttonFind) as Button
        var accBtn = findViewById<View>(R.id.buttonAccount) as Button
        crtBtn.setOnClickListener {
            startActivity(Intent(this, CreatedLobbyActivity::class.java))
        }
        fndBtn.setOnClickListener {
            startActivity(Intent(this, FindLobbyActivity::class.java))
        }
        accBtn.setOnClickListener {
            intent.putExtra("avatarId", avatarId)
            startActivity(intent)
        }
    }
}