package com.example.emenu

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emenu.data.MenuItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_menu_set.*
import kotlinx.android.synthetic.main.activity_menuorderpreview.*

@SuppressLint("Registered")
class OrderPreviewActivity :  AppCompatActivity() {

    private val TAG = "OrderPreviewActivity"
    private var mAdapter: OrderPreviewAdapter? = null
    private var firestoreDB: FirebaseFirestore? = null
    private lateinit var mBillRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menuorderpreview)
        mBillRecycler = findViewById(R.id.rvBillgenerate)
        firestoreDB = FirebaseFirestore.getInstance()
        loadOrderList()
    }

    private fun loadOrderList() {
        firestoreDB!!.collection("OrderItems")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val menuList = mutableListOf<MenuItem>()
                    for (doc in task.result!!) {
                        //val list = doc.toObject<MenuItem>(MenuItem::class.java)
                        //list.id = doc.id
                        //menuList.add(list)
                        for (value in doc.data.keys){
                            Log.d("Order", doc.data.values.indices.toString())
                            firestoreDB!!.collection("menuItems").document(value).get().addOnCompleteListener {
                                if (it.isSuccessful){
                                    var list = doc.toObject<MenuItem>(MenuItem::class.java)
                                    list.id = doc.id
                                    menuList.add(list)
                                }
                            }
                        }

                    }
                    mAdapter = OrderPreviewAdapter(menuList, this@OrderPreviewActivity, firestoreDB!!)
                    val mLayoutManager = LinearLayoutManager(this@OrderPreviewActivity)
                    mBillRecycler.layoutManager = mLayoutManager
                    mBillRecycler.adapter = mAdapter
                } else {
                    Log.d(TAG, "Error getting documents: ", task.exception)
                }
            }
    }
}