package com.example.emenu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emenu.data.MenuItem
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_menu_set.*

class CustMenuActivity : AppCompatActivity(), CustMenuListAdapter.OnMenuItemSelectedListener, View.OnClickListener
{
    private val TAG = "MainActivity"
    private var mAdapter: CustMenuListAdapter? = null
    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null
    private lateinit var orderButton: Button
    private lateinit var documentId: String
    val db = FirebaseFirestore.getInstance()
    private var itemHash: HashMap<String, String> = hashMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_set)

        val supportToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        supportToolbar.title = "E-Menu"
        orderButton = findViewById(R.id.order_button)
        orderButton.visibility = VISIBLE
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
                mAdapter =
                    CustMenuListAdapter(custmenuList, applicationContext, firestoreDB!!, this)
                rvMenuList.adapter = mAdapter
            })

        orderButton.setOnClickListener(this)
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
                    }
                    mAdapter = CustMenuListAdapter(
                        custmenuList,
                        this@CustMenuActivity,
                        firestoreDB!!,
                        this
                    )
                    val mLayoutManager = LinearLayoutManager(this@CustMenuActivity)
                    rvMenuList.layoutManager = mLayoutManager
                    rvMenuList.adapter = mAdapter
                } else {
                    Log.d(TAG, "Error getting documents: ", task.exception)
                }
            }
    }

    override fun onAddToCart(menuItem: MenuItem, orderNum: TextView) {

        val temp = orderNum.text.toString().toInt() + 1
        orderNum.text = temp.toString()

        itemHash[menuItem.id.toString()] = temp.toString()
        Log.d("Hashmap", itemHash.toString())
    }

    override fun onRemoveFromCart(menuItem: MenuItem, orderNum: TextView) {
        var temp = orderNum.text.toString().toInt()
        if (temp > 0) {
            temp -= 1
            orderNum.text = temp.toString()
        }

        itemHash[menuItem.id.toString()] = temp.toString()
        Log.d("Hashmap", itemHash.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.order_button -> onOrderSubmit()
        }
    }

    private fun onOrderSubmit() {
        val orderIntent = Intent(this, OrderPreviewActivity::class.java).apply {}
        startActivity(orderIntent)
        finish()

        var s ="CustMenuActivity"
        db.collection("OrderItems")
            .add(itemHash as Map<String, Any>)
            .addOnSuccessListener { documentReference ->
                Log.d(s, "DocumentSnapshot added with ID: ${documentReference.id}")
                documentId = documentReference.id
                Toast.makeText(
                    this@CustMenuActivity,
                    "New Ordered Item added successfully.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { e ->
                Log.w(s, "Error adding document", e)
                Toast.makeText(
                    this@CustMenuActivity,
                    "Order Item addition failed",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }
}