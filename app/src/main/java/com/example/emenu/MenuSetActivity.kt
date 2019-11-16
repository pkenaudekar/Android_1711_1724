package com.example.emenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.emenu.data.MenuItem
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_menu_set.*

class MenuSetActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private var mAdapter: MenuListAdapter? = null

    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_set)              //change it to activity_menu_set

        firestoreDB = FirebaseFirestore.getInstance()

       // loadNotesList()
        firestoreListener = firestoreDB!!.collection("notes")
            .addSnapshotListener(EventListener { documentSnapshots, e ->
                if (e != null) {
                    Log.e(TAG, "Listen failed!", e)
                    return@EventListener
                }
                val menuList = mutableListOf<MenuItem>()

                if (documentSnapshots != null) {
                    for (doc in documentSnapshots) {
                        val list = doc.toObject(MenuItem::class.java)
                        list.id = doc.id
                        menuList.add(list)
                    }
                }
                mAdapter = MenuListAdapter(menuList, applicationContext, firestoreDB!!)
                rvMenuList.adapter = mAdapter

            })

        //added new stuff
        val addButton : Button = findViewById(R.id.edit_button)
        val supportToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        supportToolbar.title = "List of Menu Items"
        setSupportActionBar(supportToolbar)

        addButton.setOnClickListener {
            val addIntent = Intent(this, AddMenuActivity::class.java).apply {  }
            startActivity(addIntent)
            finish()
        }

    }
}
