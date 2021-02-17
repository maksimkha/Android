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
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*

class FindLobbyActivity : AppCompatActivity() {
    private var database: FirebaseDatabase? = null
    lateinit var auth: FirebaseAuth
    var eventL : ValueEventListener? = null
    var ref: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_lobby)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        var fieldID = findViewById<View>(R.id.editId) as EditText
        var fieldResult = findViewById<View>(R.id.textResult) as TextView
        val textId = fieldID.text
        var fndBtn = findViewById<View>(R.id.buttonFind) as Button
        fndBtn.setOnClickListener {
            val ref = database!!.getReference("games/$textId")
            var eventL = ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.child("gameStatus").getValue<String>() == "going"){
                        Log.i("start", "started game activity")
                        var data = Intent(this@FindLobbyActivity, GameStartActivity::class.java)
                        data.putExtra("gameID", textId.toString().toInt())
                        data.putExtra("role", "guest")
                        ref.child("gameStatus").setValue("started")
                        startActivity(data)
                        finish()
                    }
                    else if(dataSnapshot.child("host").exists()){
                        val guest = database!!.getReference("games/$textId/guest")
                        guest.setValue(auth.currentUser?.uid)
                        fieldResult.text = "Waiting for game to start"
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.i("trying to read info", "error with database reading")
                }
            })
        }
    }
}