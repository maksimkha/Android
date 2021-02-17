package com.example.battleship

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class AccountActivity : AppCompatActivity() {
    private var database: FirebaseDatabase? = null
    lateinit var auth: FirebaseAuth
    var storageRef: StorageReference? = null
    var avatarId: Long = 0
    var listItems = ArrayList<String>()
    var adapter: ArrayAdapter<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        avatarId = intent.getLongExtra("avatarId", 0)
        Log.i("avatarId", avatarId.toString())
        var listView = findViewById<ListView>(R.id.list)
        adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, listItems)
        listView.adapter = adapter
        var ref = database!!.getReference("custom_users/${auth.currentUser?.uid}/games")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
               for (child in dataSnapshot.children){
                   listItems.add("Game id = ${child.key}, result =  ${child.child("result").getValue<String>()}" )
               }
                adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.i("trying to read info", "error with database reading")
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        var storageRef = Firebase.storage.reference.child("avatars/")
        var finalRef: StorageReference? = null
        var imgField = findViewById<View>(R.id.avatarImage) as ImageView
        var nameField = findViewById<View>(R.id.textName) as EditText
        Log.i("on start", "loading user info")
        var spinner = findViewById<View>(R.id.spinner) as Spinner
        spinner.setSelection(avatarId.toInt())
        val iCurrentSelection: Int = spinner.selectedItemPosition
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                return;
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == iCurrentSelection)
                    return
                Log.i("item", position.toString())
                finalRef = if (position != 0){
                    storageRef.child("avatar$position.jpg")
                } else{
                    storageRef.child("custom.jpg")
                }
                GlideApp.with(applicationContext)
                        .load(finalRef)
                        .into(imgField);

                var curUsUID = currentUser?.uid
                val avatarIdRef = database!!.getReference("custom_users/$curUsUID/avatarId")
                avatarIdRef.setValue(position)
            }

        }
        if (currentUser != null) {
            nameField.setText(currentUser.displayName.toString())
        }
        nameField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(nameField.text.toString())
                    .build()

                currentUser!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.i("changes", "User profile updated.")
                            Log.i("new profile name", nameField.text.toString())
                        }
                    }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        Log.i("on create avatar id", avatarId.toString())
        finalRef = when (avatarId){
            0.0.toLong() -> storageRef.child("custom.jpg")
            else -> storageRef.child("avatar$avatarId.jpg")
        }
        GlideApp.with(this)
                .load(finalRef)
                .into(imgField);
    }
}