package com.example.emenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emenu.data.MenuItem
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_menu_set.*
import kotlinx.android.synthetic.main.activity_menulist.*

class MenuSetActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private var mAdapter: MenuListAdapter? = null
    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_set) //change it to activity_menu_set

        firestoreDB = FirebaseFirestore.getInstance()

        loadMenuList()

        firestoreListener = firestoreDB!!.collection("MenuItems")
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
        //val addButton : Button = findViewById(R.id.edit_button)
        val supportToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        supportToolbar.title = "List of Menu Items"
        setSupportActionBar(supportToolbar)

        /*addButton.setOnClickListener {
            val addIntent = Intent(this, AddMenuActivity::class.java).apply {  }
            startActivity(addIntent)
            finish()
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()

        firestoreListener!!.remove()
    }


    private fun loadMenuList() {

        firestoreDB!!.collection("MenuItems")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val menuList = mutableListOf<MenuItem>()

                    for (doc in task.result!!) {

                        val list = doc.toObject<MenuItem>(MenuItem::class.java)
                        list.id = doc.id
                        menuList.add(list)
                        Log.d("LoadmenuList","${doc.data["imageUrl"]}")
                        //Picasso.get().load(doc.data?.get("imageUrl").toString()).into(imageView_menuImg)

                    }
                    mAdapter = MenuListAdapter(menuList, this@MenuSetActivity, firestoreDB!!)
                    val mLayoutManager = LinearLayoutManager(this@MenuSetActivity)
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

   override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
       if (item.itemId == R.id.addMenu) {
           val intent = Intent(this, AddMenuActivity::class.java)
           startActivity(intent)
       }
        return super.onOptionsItemSelected(item)
    }
}
