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
    var gameID: Int = 0
    var role: String? = ""
    var antiRole: String? = ""
    var storageRef: StorageReference? = null
    lateinit var table: TableLayout
    lateinit var enemy: TableLayout
    lateinit var startBtn: Button
    var ships: Int = 0
    var cellsInt: Int = 0
    var usedCells = Array(20) { i -> Array(2) { j -> 0 } }
    var finalCells = Array(20) { i -> Array(2) { j -> 0 } }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_start)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        table = findViewById<TableLayout>(R.id.table)
        enemy = findViewById<TableLayout>(R.id.enemy)
        gameID = intent.getIntExtra("gameID", 0)
        role = intent.getStringExtra("role")
        antiRole = when(role){
            "host" -> "guest"
            else -> "host"
        }
        var ref = database!!.getReference("games/$gameID/playersReady")
        val gameStatus = database!!.getReference("games/$gameID/gameStatus")
        var turn = database!!.getReference("games/$gameID/gameState")
        var endRef = database!!.getReference("games/$gameID/gameState/$antiRole")
        var listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()){
                    startBtn.text = "You win"
                    turn.child("turn").setValue("end")
                    turn.child("winner").setValue(auth.currentUser?.uid)
                    var tempUID = auth.currentUser?.uid
                    var gameAdd = database!!.getReference("custom_users/$tempUID/games/$gameID")
                    gameAdd.child("result").setValue("win")
                    startBtn.isClickable = true
                    startBtn.setOnClickListener(){
                        finish()
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("trying to read info", "error with database reading")
            }
        }
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.child("guest").exists() && dataSnapshot.child("host").exists()){
                    gameStatus.setValue("playing")
                    endRef.addValueEventListener(listener)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("trying to read info", "error with database reading")
            }
        })
        gameStatus.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.getValue<String>() == "playing"){
                    enemy.visibility = VISIBLE
                    startBtn.isClickable = false
                    if (role == "host") {
                        startBtn.text = "Your turn"
                    }
                    else if (role == "guest") {
                        startBtn.text = "Your opponents turn"
                        clicks(enemy)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("trying to read info", "error with database reading")
            }
        })
        turn.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.child("turn").getValue<String>() == role){
                    startBtn.text = "Your turn"
                    clicks(enemy)
                    Log.i("turn", "now ${auth.currentUser?.displayName} playing")
                }
                else if(dataSnapshot.child("turn").getValue<String>() == "end" && !dataSnapshot.child("$role").exists()){
                    startBtn.text = "You lost"
                    startBtn.isClickable = true
                    var tempUID = auth.currentUser?.uid
                    var gameAdd = database!!.getReference("custom_users/$tempUID/games/$gameID")
                    gameAdd.child("result").setValue("win")
                    Log.i("end", "game ended")
                    startBtn.setOnClickListener(){
                        finish()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("trying to read info", "error with database reading")
            }
        })
        for (i: Int in 0..9){
            var row = TableRow(this)
            row.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT/10,1f)
            for (j: Int in 0..9){
                var btn = MaterialButton(this, null, R.attr.materialButtonOutlinedStyle);
                btn.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT/10,
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
            row.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT/10,1f)
            for (j: Int in 0..9){
                var btn = MaterialButton(this, null, R.attr.materialButtonOutlinedStyle);
                btn.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT/10,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f
                )
                btn.setBackgroundColor(Color.WHITE)
                btn.setTextColor(Color.BLACK)
                btn.id = i*10 + j
                row.addView(btn, j)
                btn.setOnClickListener(){
                    var refernence = database!!.getReference("games/$gameID/gameState")
                    refernence.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if(dataSnapshot.child("$antiRole/x$j+y$i").exists()){
                                refernence.child("$antiRole/x$j+y$i").removeValue()
                                startBtn.text = "Hit! Go more!"
                                var tempRow = enemy.getChildAt(i) as TableRow
                                var tempCell = tempRow.getChildAt(j) as Button
                                tempCell.setBackgroundColor(Color.BLACK)
                                clicks(enemy)
                                Log.i("hit", "user ${auth.currentUser?.displayName} hit")
                            }
                            else if (!dataSnapshot.child("$antiRole/x$j+y$i").exists()){
                                startBtn.text = "Miss! Ur opponents turn!"
                                var tempRow = enemy.getChildAt(i) as TableRow
                                var tempCell = tempRow.getChildAt(j) as Button
                                tempCell.setBackgroundColor(Color.GRAY)
                                clicks(enemy)
                                refernence.child("turn").setValue("$antiRole")
                                Log.i("missed", "user ${auth.currentUser?.displayName} missed")
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.i("trying to read info", "error with database reading")
                        }
                    })
                }
            }
            enemy.addView(row, i)
        }
        enemy.visibility = INVISIBLE
        startBtn = findViewById<Button>(R.id.buttonStart)
        startBtn.setOnClickListener {
            when (ships){
                0->{
                    checkFour()
                    checkOther()
                }
                1->{
                    checkThree()
                    checkThree()
                    checkOther()
                }
                3->{
                    checkTwo()
                    checkTwo()
                    checkTwo()
                    checkOther()
                }
                6->{
                    checkOne()
                    checkOne()
                    checkOne()
                    checkOne()
                    checkOther()
                    if (ships == 10) {
                        ref.child(role.toString()).setValue("true")
                        gameStart()
                    }
                }
            }
        }
    }

    fun gameStart(){
        clicks(table)
        for (i: Int in finalCells.indices){
            var x = finalCells[i][0]
            var y = finalCells[i][1]
            var dotRef = database!!.getReference("games/$gameID/gameState/$role/x$x+y$y")
            dotRef.setValue("true")
        }
    }

    fun checkOne(){
        for (i: Int in 0..9){
            var row: TableRow = table.getChildAt(i) as TableRow
            for (j: Int in 0..9) {
                var cell: Button = row.getChildAt(j) as Button
                if(cell.text == "1"){
                    usedCells[cellsInt][0] = j
                    usedCells[cellsInt][1] = i
                    cell.text = "7"
                    cellsInt+=1
                    ships+=1
                    startBtn.text = "Waiting for your opponent"
                    return
                }
            }
        }
    }

    fun checkTwo(){
        for (i: Int in 0..9){
            var row: TableRow = table.getChildAt(i) as TableRow
            for (j: Int in 0..9) {
                var cell: Button = row.getChildAt(j) as Button
                if(cell.text == "1"){
                    var cellNext1: Button? = row.getChildAt(j+1) as Button?
                    if (cellNext1 != null) {
                        if (cellNext1.text == "1"){
                                for (k: Int in cellsInt..cellsInt+1) {
                                    usedCells[k][0] = j + k - cellsInt
                                    usedCells[k][1] = i
                                }
                                cell.text = "6"
                                cellNext1.text = "6"
                                ships+=1
                                startBtn.text = "Place four 1dotted ships"
                                cellsInt+=2
                                return
                            }
                        else if (cellNext1.text == "") {
                            var rowNext1: TableRow? = table.getChildAt(i + 1) as TableRow?
                            var cellNext1: Button? = rowNext1?.getChildAt(j) as Button?
                            if (cellNext1 != null) {
                                if (cellNext1.text == "1") {
                                    for (k: Int in cellsInt..cellsInt+1) {
                                        usedCells[i][0] = j
                                        usedCells[i][1] = i + k - cellsInt
                                    }
                                    cell.text = "6"
                                    cellNext1.text = "6"
                                    ships+=1
                                    startBtn.text = "Place four 1dotted ships"
                                    cellsInt += 2
                                    return
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun checkThree(){
        for (i: Int in 0..9) {
            var row: TableRow = table.getChildAt(i) as TableRow
            for (j: Int in 0..9) {
                var cell: Button = row.getChildAt(j) as Button
                if(cell.text == "1"){
                    var cellNext1: Button? = row.getChildAt(j+1) as Button?
                    if (cellNext1 != null) {
                        if (cellNext1.text == "1"){
                            var cellNext2: Button? = row.getChildAt(j+2) as Button?
                            if (cellNext2 != null) {
                                if (cellNext2.text == "1"){
                                    for (k: Int in cellsInt..cellsInt+2) {
                                        usedCells[k][0] = j + k - cellsInt
                                        usedCells[k][1] = i
                                    }
                                    cell.text = "5"
                                    cellNext1.text = "5"
                                    cellNext2.text = "5"
                                    ships+=1
                                    startBtn.text = "Place three 2dotted ship"
                                    cellsInt+=3
                                    return
                                }
                            }
                        } else if (cellNext1.text == ""){
                            var rowNext1: TableRow? = table.getChildAt(i+1) as TableRow?
                            var cellNext1: Button? = rowNext1?.getChildAt(j) as Button?
                            var rowNext2: TableRow? = table.getChildAt(i+2) as TableRow?
                            var cellNext2: Button? = rowNext2?.getChildAt(j) as Button?
                            if (cellNext1 != null) {
                                if (cellNext2 != null) {
                                    if (cellNext1.text == "1" && cellNext2.text == "1"){
                                        for (k: Int in cellsInt..cellsInt+2) {
                                            usedCells[i][0] = j
                                            usedCells[i][1] = i + k - cellsInt
                                        }
                                        cell.text = "5"
                                        cellNext1.text = "5"
                                        cellNext2.text = "5"
                                        ships+=1
                                        startBtn.text = "Place three 2dotted ship"
                                        cellsInt+=3
                                        return
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun checkFour(){
        for (i: Int in 0..9){
            var row: TableRow = table.getChildAt(i) as TableRow
            for (j: Int in 0..9){
                var cell: Button = row.getChildAt(j) as Button
                if(cell.text == "1"){
                    var cellNext1: Button? = row.getChildAt(j+1) as Button?
                    if (cellNext1 != null) {
                        if (cellNext1.text == "1"){
                            var cellNext2: Button? = row.getChildAt(j+2) as Button?
                            var cellNext3: Button? = row.getChildAt(j+3) as Button?
                            if (cellNext2 != null) {
                                if (cellNext3 != null) {
                                    if (cellNext2.text == "1" && cellNext3.text == "1"){
                                        for (k: Int in 0..3) {
                                            usedCells[k][0] = j + k
                                            usedCells[k][1] = i
                                        }
                                        cell.text = "4"
                                        cellNext1.text = "4"
                                        cellNext2.text = "4"
                                        cellNext3.text = "4"
                                        ships+=1
                                        startBtn.text = "Place two 3dotted ship"
                                        cellsInt+=4
                                        return
                                    }
                                }
                            }
                        } else if (cellNext1.text == ""){
                            var rowNext1: TableRow? = table.getChildAt(i+1) as TableRow?
                            var cellNext1: Button? = rowNext1?.getChildAt(j) as Button?
                            var rowNext2: TableRow? = table.getChildAt(i+2) as TableRow?
                            var cellNext2: Button? = rowNext2?.getChildAt(j) as Button?
                            var rowNext3: TableRow? = table.getChildAt(i+3) as TableRow?
                            var cellNext3: Button? = rowNext3?.getChildAt(j) as Button?
                            if (cellNext1 != null) {
                                if (cellNext2 != null) {
                                    if (cellNext3 != null) {
                                        if (cellNext1.text == "1" && cellNext2.text == "1" && cellNext3.text == "1"){
                                            for (k: Int in 0..3) {
                                                usedCells[i][0] = j
                                                usedCells[i][1] = i + k
                                            }
                                            cell.text = "4"
                                            cellNext1.text = "4"
                                            cellNext2.text = "4"
                                            cellNext3.text = "4"
                                            ships+=1
                                            startBtn.text = "Place two 3dotted ship"
                                            cellsInt+=4
                                            return
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun checkOther(){
        var error = false
        for (i: Int in 0..9) {
            var row: TableRow = table.getChildAt(i) as TableRow
            for (j: Int in 0..9) {
                var cell: Button = row.getChildAt(j) as Button
                if(cell.text == "1"){
                    cell.text = ""
                    cell.setBackgroundColor(Color.WHITE)
                    error = true
                }
            }
        }
        if (error){
            when (ships){
                1->{
                    setDef("4")
                    startBtn.text = "Place 4dotted ship"
                    cellsInt=0
                    ships = 0
                }
                2, 3->{
                    setDef("5")
                    startBtn.text = "Place two 3dotted ship"
                    cellsInt=4
                    ships = 1
                }
                4, 5, 6->{
                    setDef("6")
                    startBtn.text = "Place tree 2dotted ship"
                    cellsInt=10
                    ships = 3
                }
                7, 8, 9, 10->{
                    setDef("7")
                    startBtn.text = "Place four 1dotted ship"
                    cellsInt=16
                    ships = 6
                }
            }
        }
        else{
            for(i: Int in 0 until cellsInt){
                finalCells[i][0] = usedCells[i][0]
                finalCells[i][1] = usedCells[i][1]
                for(j: Int in -1..1) {
                    var row: TableRow? = table.getChildAt(finalCells[i][1]+j) as TableRow?
                    for (k: Int in -1..1) {
                        var cell: Button? = row?.getChildAt(finalCells[i][0]+k) as Button?
                        if (cell != null) {
                            cell.isClickable = false
                        }
                    }
                }
            }
        }
    }

    fun setDef(am: String){
        for (i: Int in 0..9){
            var row: TableRow = table.getChildAt(i) as TableRow
            for(j: Int in 0..9){
                var cell: Button = row.getChildAt(j) as Button
                if(cell.text == am){
                    cell.text = ""
                    cell.setBackgroundColor(Color.WHITE)
                }
            }
        }
    }

    fun clicks(table : TableLayout){
        for (i: Int in 0..9){
            var row: TableRow = table.getChildAt(i) as TableRow
            for(j: Int in 0..9){
                var cell: Button = row.getChildAt(j) as Button
                cell.isClickable = !cell.isClickable
            }
        }
    }
}