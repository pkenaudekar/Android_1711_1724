package com.example.emenu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.emenu.adapter.ListAdapter
import com.example.emenu.data.model.ItemList
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_account_list.*
import kotlinx.android.synthetic.main.list_items.*


class AccountListActivity : AppCompatActivity() {

    private val s = "AccountListActivity"
    private var lAdapter: ListAdapter? = null
    private var sAdapter: ListAdapter? = null
    private var db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    private var listener: ListenerRegistration? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_list)

        loadItemList()
        swapItemList()

        listener = db!!.collection("LoginAccount")
            .addSnapshotListener(EventListener { documentSnapshots, e ->
                if (e != null) {
                    Log.e(s, "Listen failed!", e)
                    return@EventListener
                }

                val itemList = mutableListOf<ItemList>()

                if (documentSnapshots != null) {
                    for (doc in documentSnapshots) {
                        val item = doc.toObject(ItemList::class.java)
                        item.id = doc.id
                        itemList.add(item)

                    }
                }

                lAdapter = ListAdapter(itemList, applicationContext, db!!)
                list_view.adapter = lAdapter
            })



        btnAddAcc.setOnClickListener {
            val intentAccSet = Intent(this, AccSetActivity::class.java)
            startActivity(intentAccSet)
            finish()
        }

        btnBackAcc.setOnClickListener {
            val intentAccSet = Intent(this, OptionsActivity::class.java)
            startActivity(intentAccSet)
            finish()
        }


    }

   // ItemTouchHelper.SimpleCallBack.

    private fun swapItemList(){
        /*
        val itemList = mutableListOf<ItemList>()
        sAdapter = ListAdapter(itemList, this@AccountListActivity, db!!)
        val mLayoutManager = LinearLayoutManager(this@AccountListActivity)
        list_view.layoutManager = mLayoutManager
        //list_view.itemAnimator = DefaultItemAnimator()
        list_view.adapter = sAdapter*/
/*
        val itemList = mutableListOf<ItemList>()

        list_view.layoutManager = LinearLayoutManager(this@AccountListActivity)
        list_view.adapter = ListAdapter(itemList, this@AccountListActivity, db!!)

        val swipeHandler = object : SwipeToDeleteCallback(this@AccountListActivity) {
            override fun onBindViewHolder(holder: ListAdapter.ViewHolder?, position: Int) {
                val item = itemList[position]

            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = list_view.adapter as ListAdapter
                Log.d("viewholder itemid",viewHolder.itemId.toString())
                adapter.removeAt(item.id.toString(),viewHolder.adapterPosition)

                loadItemList()
            }
        }


        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(list_view)*/

        val itemList = mutableListOf<ItemList>()

        list_view.layoutManager = LinearLayoutManager(this@AccountListActivity)
        list_view.adapter = ListAdapter(itemList, this@AccountListActivity, db!!)

        //Swipe Left Action
        val swipeHandlerLeft = object : SwipeToDeleteCallback(this@AccountListActivity) {
            override fun onBindViewHolder(holder: ListAdapter.ViewHolder?, position: Int) {

            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                //val adapter = list_view.adapter as ListAdapter
                //val item = itemList[position]
                //Log.d("Doc ID: ",position.toString())
                //adapter.removeAt(this@AccountListActivity.findViewById<>(R.id.doc_number).toString(),viewHolder.adapterPosition)
                loadItemList()
            }

        }

        //Swipe Right Action
        val swipeHandlerRight = object : SwipeToEditCallback(this@AccountListActivity) {
            override fun onBindViewHolder(holder: ListAdapter.ViewHolder?, position: Int) {
                ///holder.docNumber.to

            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                //val adapter = list_view.adapter as ListAdapter
                //val item = itemList[position]
               // Log.d("Document id ",item.docId.toString())
                //adapter.removeAt(doc_number.toString(),viewHolder.adapterPosition)
                loadItemList()
            }
        }
        val itemTouchHelperLeft = ItemTouchHelper(swipeHandlerLeft)
        itemTouchHelperLeft.attachToRecyclerView(list_view)

        val itemTouchHelperRight = ItemTouchHelper(swipeHandlerRight)
        itemTouchHelperRight.attachToRecyclerView(list_view)

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
                    var acc = 0
                    for (doc in task.result!!) {
                        acc++
                        val item = doc.toObject<ItemList>(ItemList::class.java)
                        item.id = doc.id
                        //Log.d("doc id",doc.id)
                        item.username = doc.get("Username").toString()
                        item.name = doc.get("Name").toString()
                        item.password = doc.get("Password").toString()
                        item.phoneNo = doc.get("Phone Number").toString()
                        item.accType = doc.get("Type").toString()
                        item.docId = doc.id
                        item.itemNumber = acc.toString()
                        itemList.add(item)
                    }

                    lAdapter = ListAdapter(itemList, this@AccountListActivity, db!!)
                    val mLayoutManager = LinearLayoutManager(this@AccountListActivity)
                    list_view.layoutManager = mLayoutManager
                    //list_view.itemAnimator = DefaultItemAnimator()
                    list_view.adapter = lAdapter
                } else {
                    Log.d(s, "Error getting documents: ", task.exception)
                }
            }
    }


}
