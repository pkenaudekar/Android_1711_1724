package com.example.emenu

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emenu.data.MenuItem
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_menu_set.*

class CustMenuActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var mAdapter: CustMenuListAdapter? = null
    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_set)


        val supportToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        supportToolbar.title = "E-Menu"
        setSupportActionBar(supportToolbar)

        firestoreDB = FirebaseFirestore.getInstance()

        loadMenuList()

        firestoreListener = firestoreDB!!.collection("MenuItems")
            .addSnapshotListener(EventListener { documentSnapshots, e ->
                if (e != null) {
                    Log.e(TAG, "Listen failed!", e)
                    return@EventListener
                }
                val custmenuList = mutableListOf<MenuItem>()

                if (documentSnapshots != null) {
                    for (doc in documentSnapshots) {

                        val list = doc.toObject(MenuItem::class.java)
                        list.id = doc.id
                        custmenuList.add(list)
                    }
                }
                mAdapter = CustMenuListAdapter(custmenuList, applicationContext, firestoreDB!!)
                rvMenuList.adapter = mAdapter

            })
    }

    private fun loadMenuList() {

        firestoreDB!!.collection("MenuItems")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val custmenuList = mutableListOf<MenuItem>()

                    for (doc in task.result!!) {

                        val list = doc.toObject<MenuItem>(MenuItem::class.java)
                        list.id = doc.id
                        custmenuList.add(list)
                        Log.d("LoadmenuList", "${doc.data["imageUrl"]}")
                        //Picasso.get().load(doc.data?.get("imageUrl").toString()).into(imageView_menuImg)

                    }
                    mAdapter =
                        CustMenuListAdapter(custmenuList, this@CustMenuActivity, firestoreDB!!)
                    val mLayoutManager = LinearLayoutManager(this@CustMenuActivity)
                    rvMenuList.layoutManager = mLayoutManager
                    rvMenuList.adapter = mAdapter
                } else {
                    Log.d(TAG, "Error getting documents: ", task.exception)
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }
}