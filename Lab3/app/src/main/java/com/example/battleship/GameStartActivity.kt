package com.example.battleship

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference


class GameStartActivity : AppCompatActivity() {
    private var database: FirebaseDatabase? = null
    lateinit var auth: FirebaseAuth
    var gameStateModel: GameViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_start)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        var table = findViewById<TableLayout>(R.id.table)
        var enemy = findViewById<TableLayout>(R.id.enemy)
        var gameID = intent.getIntExtra("gameID", 0)
        var role = intent.getStringExtra("role")
        var antiRole = when(role){
            "host" -> "guest"
            else -> "host"
        }
        var startBtn = findViewById<Button>(R.id.buttonStart)
        var ref = database!!.getReference("games/$gameID/playersReady")
        val gameStatus = database!!.getReference("games/$gameID/gameStatus")
        var turn = database!!.getReference("games/$gameID/gameState")
        var endRef = database!!.getReference("games/$gameID/gameState/$antiRole")
        gameStateModel = ViewModelProviders.of(this)[GameViewModel::class.java]
        gameStateModel!!.GameViewModelConstr(gameID, role.toString(), antiRole.toString(), database!!, auth)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child("guest").exists() && dataSnapshot.child("host").exists()) {
                    gameStatus.setValue("playing")
                    gameStateModel!!.setEndRefListener(endRef, enemy, startBtn)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("trying to read info", "error with database reading")
            }
        })
        gameStateModel!!.setGameStatListener(endRef, enemy, startBtn)
        gameStateModel!!.setTurnListener(endRef, enemy, startBtn)
        for (i: Int in 0..9){
            var row = TableRow(this)
            row.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT / 10,
                1f
            )
            for (j: Int in 0..9){
                var btn = MaterialButton(this, null, R.attr.materialButtonOutlinedStyle);
                btn.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT / 10,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f
                )
                btn.setBackgroundColor(Color.WHITE)
                btn.setTextColor(Color.BLACK)
                btn.id = i*1000 + j*100
                row.addView(btn, j)
                btn.setOnClickListener(){
                    if (btn.text == "") {
                        btn.text = "1"
                        btn.setBackgroundColor(Color.BLACK)
                    }
                    else if (btn.text == "1"){
                        btn.text = ""
                        btn.setBackgroundColor(Color.WHITE)
                    }
                }
            }
            table.addView(row, i)
        }
        for (i: Int in 0..9){
            var row = TableRow(this)
            row.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT / 10,
                1f
            )
            for (j: Int in 0..9){
                var btn = MaterialButton(this, null, R.attr.materialButtonOutlinedStyle);
                btn.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT / 10,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f
                )
                btn.setBackgroundColor(Color.WHITE)
                btn.setTextColor(Color.BLACK)
                btn.id = i*10 + j
                row.addView(btn, j)
                btn.setOnClickListener(){
                    gameStateModel!!.setBtnListener(table, startBtn, j, i)
                }
            }
            enemy.addView(row, i)
        }
        enemy.visibility = INVISIBLE
        startBtn.setOnClickListener {
            gameStateModel!!.setStartBtn(table, enemy, startBtn)
        }
    }


}