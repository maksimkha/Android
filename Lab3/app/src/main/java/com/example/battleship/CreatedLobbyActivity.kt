package com.example.battleship

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class CreatedLobbyActivity : AppCompatActivity() {
    var gameIDR: Int = (0..100000).random()
    private var database: FirebaseDatabase? = null
    lateinit var auth: FirebaseAuth
    var eventL : ValueEventListener? = null
    var ref: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_created_lobby)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        var startBtn = findViewById<View>(R.id.buttonStart) as Button
        ref = database!!.getReference("games")
        val host = database!!.getReference("games/$gameIDR/host")
        host.setValue(auth.currentUser?.uid)
        val gameStatus = database!!.getReference("games/$gameIDR/gameStatus")
        gameStatus.setValue("created")
        var gameField = findViewById<View>(R.id.textId2) as TextView
        var opponentField = findViewById<View>(R.id.textName) as TextView
        gameField.text = gameIDR.toString()
        eventL = ref!!.child(gameIDR.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.child("guest").exists()){
                    startBtn.visibility = VISIBLE
                    opponentField.text = dataSnapshot.child("guest").getValue<String>()
                    gameStatus.setValue("started")
                    Log.i("ready", "game can start")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("trying to read info", "error with database reading")
            }
        })
        startBtn.setOnClickListener {
            gameStatus.setValue("going")
            var data = Intent(this, GameStartActivity::class.java)
            data.putExtra("gameID", gameIDR)
            data.putExtra("role", "host")
            startActivity(data)
            finish()
        }
    }

    override fun onDestroy() {
        eventL?.let { ref?.removeEventListener(it) }
        super.onDestroy()
    }
}