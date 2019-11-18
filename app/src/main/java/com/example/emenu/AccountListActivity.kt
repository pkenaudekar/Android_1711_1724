package com.example.emenu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emenu.adapter.ListAdapter
import com.example.emenu.data.model.ItemList
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_account_list.*
import kotlinx.android.synthetic.main.activity_options.*


class AccountListActivity : AppCompatActivity() {
/*
    // Access a Cloud Firestore instance from your Activity
    private val db = FirebaseFirestore.getInstance()

    // Reference to a Collection
    private val loginAccountRef = db.collection("LoginAccount")
    private var adapter: ListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_list)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val query = loginAccountRef.orderBy("username", Query.Direction.DESCENDING)

        val options = FirestoreRecyclerOptions.Builder<List>()
            .setQuery(query, List::class.java)
            .build()

        adapter = ListAdapter(options)

        val recyclerView: RecyclerView  = findViewById(R.id.list_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.setAdapter(adapter)
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }
*/
    private val s = "AccountListActivity"

    private var adapter: ListAdapter? = null

    private var db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    private var listener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_list)

        //db =

        loadItemList()

        listener = db!!.collection("LoginAccount")
            .addSnapshotListener(EventListener { documentSnapshots, e ->
                if (e != null) {
                    Log.e(s, "Listen failed!", e)
                    return@EventListener
                }

                val itemList = mutableListOf<ItemList>()

                for (doc in documentSnapshots!!)
                {
                    val item = doc.toObject(ItemList::class.java)
                    item.id = doc.id
                    itemList.add(item)

                }

                adapter = ListAdapter(itemList, applicationContext, db!!)
                list_view.adapter = adapter
            })

        btnAddAcc.setOnClickListener {
            val intentAccSet = Intent(this, AccSetActivity::class.java)
            startActivity(intentAccSet)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        listener!!.remove()
    }

    private fun loadItemList() {
        db!!.collection("LoginAccount")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val itemList = mutableListOf<ItemList>()

                    for (doc in task.result!!) {
                        val item = doc.toObject<ItemList>(ItemList::class.java)
                        item.id = doc.id
                        itemList.add(item)
                    }

                    adapter = ListAdapter(itemList, applicationContext, db!!)
                    val mLayoutManager = LinearLayoutManager(applicationContext)
                    list_view.layoutManager = mLayoutManager
                    list_view.itemAnimator = DefaultItemAnimator()
                    list_view.adapter = adapter
                } else {
                    Log.d(s, "Error getting documents: ", task.exception)
                }
            }
    }
}
