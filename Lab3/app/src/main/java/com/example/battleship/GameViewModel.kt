package com.example.battleship

import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference

class GameViewModel  : ViewModel() {
    private var database: FirebaseDatabase? = null
    lateinit var auth: FirebaseAuth
    var gameID: Int = 0
    var role: String? = ""
    var antiRole: String? = ""
    var ships: Int = 0
    var cellsInt: Int = 0
    var usedCells = Array(20) { i -> Array(2) { j -> 0 } }
    var finalCells = Array(20) { i -> Array(2) { j -> 0 } }

    public fun GameViewModelConstr(
            gameID: Int,
            role: String,
            antiRole: String,
            database: FirebaseDatabase,
            auth: FirebaseAuth
    ) {
        this.gameID = gameID
        this.role = role
        this.antiRole = antiRole
        this.database = database
        this.auth = auth
    }

    fun gameStart(table: TableLayout) {
        clicks(table)
        for (i: Int in finalCells.indices) {
            var x = finalCells[i][0]
            var y = finalCells[i][1]
            var dotRef = database!!.getReference("games/$gameID/gameState/$role/x$x+y$y")
            dotRef.setValue("true")
        }
    }

    fun checkOne(table: TableLayout, startBtn: Button) {
        for (i: Int in 0..9) {
            var row: TableRow = table.getChildAt(i) as TableRow
            for (j: Int in 0..9) {
                var cell: Button = row.getChildAt(j) as Button
                if (cell.text == "1") {
                    usedCells[cellsInt][0] = j
                    usedCells[cellsInt][1] = i
                    cell.text = "7"
                    cellsInt += 1
                    ships += 1
                    startBtn.text = "Waiting for your opponent"
                    return
                }
            }
        }
    }

    fun checkTwo(table: TableLayout, startBtn: Button) {
        for (i: Int in 0..9) {
            var row: TableRow = table.getChildAt(i) as TableRow
            for (j: Int in 0..9) {
                var cell: Button = row.getChildAt(j) as Button
                if (cell.text == "1") {
                    var cellNext1: Button? = row.getChildAt(j + 1) as Button?
                    if (cellNext1 != null) {
                        if (cellNext1.text == "1") {
                            for (k: Int in cellsInt..cellsInt + 1) {
                                usedCells[k][0] = j + k - cellsInt
                                usedCells[k][1] = i
                            }
                            cell.text = "6"
                            cellNext1.text = "6"
                            ships += 1
                            startBtn.text = "Place four 1dotted ships"
                            cellsInt += 2
                            return
                        } else if (cellNext1.text == "") {
                            var rowNext1: TableRow? = table.getChildAt(i + 1) as TableRow?
                            var cellNext1: Button? = rowNext1?.getChildAt(j) as Button?
                            if (cellNext1 != null) {
                                if (cellNext1.text == "1") {
                                    for (k: Int in cellsInt..cellsInt + 1) {
                                        usedCells[i][0] = j
                                        usedCells[i][1] = i + k - cellsInt
                                    }
                                    cell.text = "6"
                                    cellNext1.text = "6"
                                    ships += 1
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

    fun checkThree(table: TableLayout, startBtn: Button) {
        for (i: Int in 0..9) {
            var row: TableRow = table.getChildAt(i) as TableRow
            for (j: Int in 0..9) {
                var cell: Button = row.getChildAt(j) as Button
                if (cell.text == "1") {
                    var cellNext1: Button? = row.getChildAt(j + 1) as Button?
                    if (cellNext1 != null) {
                        if (cellNext1.text == "1") {
                            var cellNext2: Button? = row.getChildAt(j + 2) as Button?
                            if (cellNext2 != null) {
                                if (cellNext2.text == "1") {
                                    for (k: Int in cellsInt..cellsInt + 2) {
                                        usedCells[k][0] = j + k - cellsInt
                                        usedCells[k][1] = i
                                    }
                                    cell.text = "5"
                                    cellNext1.text = "5"
                                    cellNext2.text = "5"
                                    ships += 1
                                    startBtn.text = "Place three 2dotted ship"
                                    cellsInt += 3
                                    return
                                }
                            }
                        } else if (cellNext1.text == "") {
                            var rowNext1: TableRow? = table.getChildAt(i + 1) as TableRow?
                            var cellNext1: Button? = rowNext1?.getChildAt(j) as Button?
                            var rowNext2: TableRow? = table.getChildAt(i + 2) as TableRow?
                            var cellNext2: Button? = rowNext2?.getChildAt(j) as Button?
                            if (cellNext1 != null) {
                                if (cellNext2 != null) {
                                    if (cellNext1.text == "1" && cellNext2.text == "1") {
                                        for (k: Int in cellsInt..cellsInt + 2) {
                                            usedCells[i][0] = j
                                            usedCells[i][1] = i + k - cellsInt
                                        }
                                        cell.text = "5"
                                        cellNext1.text = "5"
                                        cellNext2.text = "5"
                                        ships += 1
                                        startBtn.text = "Place three 2dotted ship"
                                        cellsInt += 3
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

    fun checkFour(table: TableLayout, startBtn: Button) {
        for (i: Int in 0..9) {
            var row: TableRow = table.getChildAt(i) as TableRow
            for (j: Int in 0..9) {
                var cell: Button = row.getChildAt(j) as Button
                if (cell.text == "1") {
                    var cellNext1: Button? = row.getChildAt(j + 1) as Button?
                    if (cellNext1 != null) {
                        if (cellNext1.text == "1") {
                            var cellNext2: Button? = row.getChildAt(j + 2) as Button?
                            var cellNext3: Button? = row.getChildAt(j + 3) as Button?
                            if (cellNext2 != null) {
                                if (cellNext3 != null) {
                                    if (cellNext2.text == "1" && cellNext3.text == "1") {
                                        for (k: Int in 0..3) {
                                            usedCells[k][0] = j + k
                                            usedCells[k][1] = i
                                        }
                                        cell.text = "4"
                                        cellNext1.text = "4"
                                        cellNext2.text = "4"
                                        cellNext3.text = "4"
                                        ships += 1
                                        startBtn.text = "Place two 3dotted ship"
                                        cellsInt += 4
                                        return
                                    }
                                }
                            }
                        } else if (cellNext1.text == "") {
                            var rowNext1: TableRow? = table.getChildAt(i + 1) as TableRow?
                            var cellNext1: Button? = rowNext1?.getChildAt(j) as Button?
                            var rowNext2: TableRow? = table.getChildAt(i + 2) as TableRow?
                            var cellNext2: Button? = rowNext2?.getChildAt(j) as Button?
                            var rowNext3: TableRow? = table.getChildAt(i + 3) as TableRow?
                            var cellNext3: Button? = rowNext3?.getChildAt(j) as Button?
                            if (cellNext1 != null) {
                                if (cellNext2 != null) {
                                    if (cellNext3 != null) {
                                        if (cellNext1.text == "1" && cellNext2.text == "1" && cellNext3.text == "1") {
                                            for (k: Int in 0..3) {
                                                usedCells[i][0] = j
                                                usedCells[i][1] = i + k
                                            }
                                            cell.text = "4"
                                            cellNext1.text = "4"
                                            cellNext2.text = "4"
                                            cellNext3.text = "4"
                                            ships += 1
                                            startBtn.text = "Place two 3dotted ship"
                                            cellsInt += 4
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

    fun checkOther(table: TableLayout, startBtn: Button) {
        var error = false
        for (i: Int in 0..9) {
            var row: TableRow = table.getChildAt(i) as TableRow
            for (j: Int in 0..9) {
                var cell: Button = row.getChildAt(j) as Button
                if (cell.text == "1") {
                    cell.text = ""
                    cell.setBackgroundColor(Color.WHITE)
                    error = true
                }
            }
        }
        if (error) {
            when (ships) {
                1 -> {
                    setDef("4", table)
                    startBtn.text = "Place 4dotted ship"
                    cellsInt = 0
                    ships = 0
                }
                2, 3 -> {
                    setDef("5", table)
                    startBtn.text = "Place two 3dotted ship"
                    cellsInt = 4
                    ships = 1
                }
                4, 5, 6 -> {
                    setDef("6", table)
                    startBtn.text = "Place tree 2dotted ship"
                    cellsInt = 10
                    ships = 3
                }
                7, 8, 9, 10 -> {
                    setDef("7", table)
                    startBtn.text = "Place four 1dotted ship"
                    cellsInt = 16
                    ships = 6
                }
            }
        } else {
            for (i: Int in 0 until cellsInt) {
                finalCells[i][0] = usedCells[i][0]
                finalCells[i][1] = usedCells[i][1]
                for (j: Int in -1..1) {
                    var row: TableRow? = table.getChildAt(finalCells[i][1] + j) as TableRow?
                    for (k: Int in -1..1) {
                        var cell: Button? = row?.getChildAt(finalCells[i][0] + k) as Button?
                        if (cell != null) {
                            cell.isClickable = false
                        }
                    }
                }
            }
        }
    }

    fun setDef(am: String, table: TableLayout) {
        for (i: Int in 0..9) {
            var row: TableRow = table.getChildAt(i) as TableRow
            for (j: Int in 0..9) {
                var cell: Button = row.getChildAt(j) as Button
                if (cell.text == am) {
                    cell.text = ""
                    cell.setBackgroundColor(Color.WHITE)
                }
            }
        }
    }

    fun clicks(table: TableLayout) {
        for (i: Int in 0..9) {
            var row: TableRow = table.getChildAt(i) as TableRow
            for (j: Int in 0..9) {
                var cell: Button = row.getChildAt(j) as Button
                cell.isClickable = !cell.isClickable
            }
        }
    }

    fun setGameStatListener(ref: DatabaseReference, enemy: TableLayout, startBtn: Button) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.getValue<String>() == "playing") {
                    enemy.visibility = View.VISIBLE
                    startBtn.isClickable = false
                    if (role == "host") {
                        startBtn.text = "Your turn"
                    } else if (role == "guest") {
                        startBtn.text = "Your opponents turn"
                        clicks(enemy)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("trying to read info", "error with database reading")
            }
        })
    }

    fun setTurnListener(ref: DatabaseReference, enemy: TableLayout, startBtn: Button) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child("turn").getValue<String>() == role) {
                    startBtn.text = "Your turn"
                    clicks(enemy)
                    Log.i("turn", "now ${auth.currentUser?.displayName} playing")
                } else if (dataSnapshot.child("turn")
                                .getValue<String>() == "end" && !dataSnapshot.child(
                                "$role"
                        ).exists()
                ) {
                    startBtn.text = "You lost"
                    startBtn.isClickable = true
                    var tempUID = auth.currentUser?.uid
                    var gameAdd = database!!.getReference("custom_users/$tempUID/games/$gameID")
                    gameAdd.child("result").setValue("win")
                    Log.i("end", "game ended")
                    startBtn.setOnClickListener() {
                        val activity: Activity = this as Activity
                        activity.finish()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("trying to read info", "error with database reading")
            }
        })
    }

    fun setEndRefListener(ref: DatabaseReference, enemy: TableLayout, startBtn: Button) {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    startBtn.text = "You win"
                    var turn = database!!.getReference("games/$gameID/gameState")
                    turn.child("turn").setValue("end")
                    turn.child("winner").setValue(auth.currentUser?.uid)
                    var tempUID = auth.currentUser?.uid
                    var gameAdd = database!!.getReference("custom_users/$tempUID/games/$gameID")
                    gameAdd.child("result").setValue("win")
                    startBtn.isClickable = true
                    startBtn.setOnClickListener() {
                        val activity: Activity = this as Activity
                        activity.finish()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("trying to read info", "error with database reading")
            }
        })
    }

    fun setBtnListener(enemy: TableLayout, startBtn : Button, j: Int, i : Int){
        var refernence = database!!.getReference("games/$gameID/gameState")
        refernence.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var refernence = database!!.getReference("games/$gameID/gameState")
                if (dataSnapshot.child("$antiRole/x$j+y$i").exists()) {
                    refernence.child("$antiRole/x$j+y$i").removeValue()
                    startBtn.text = "Hit! Go more!"
                    var tempRow = enemy.getChildAt(i) as TableRow
                    var tempCell = tempRow.getChildAt(j) as Button
                    tempCell.setBackgroundColor(Color.BLACK)
                    clicks(enemy)
                    Log.i("hit", "user ${auth.currentUser?.displayName} hit")
                } else if (!dataSnapshot.child("$antiRole/x$j+y$i").exists()) {
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

    fun setStartBtn(table: TableLayout, enemy: TableLayout, startBtn: Button){
        var ref = database!!.getReference("games/$gameID/playersReady")
        when (ships){
            0 -> {
                checkFour(table, startBtn)
                checkOther(table, startBtn)
            }
            1 -> {
                checkThree(table, startBtn)
                checkThree(table, startBtn)
                checkOther(table, startBtn)
            }
            3 -> {
                checkTwo(table, startBtn)
                checkTwo(table, startBtn)
                checkTwo(table, startBtn)
                checkOther(table, startBtn)
            }
            6 -> {
                checkOne(table, startBtn)
                checkOne(table, startBtn)
                checkOne(table, startBtn)
                checkOne(table, startBtn)
                checkOther(table, startBtn)
                if (ships == 10) {
                    ref.child(role.toString()).setValue("true")
                    gameStart(table)
                }
            }
        }
    }
}