package com.example.emenu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emenu.adapter.ListAdapter
import com.example.emenu.data.model.ItemList
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_account_list.*
import kotlinx.android.synthetic.main.activity_login.*


class AccountListActivity : AppCompatActivity() {

    private val s = "AccountListActivity"
    private var lAdapter: ListAdapter? = null
    private var db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    private var listener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_list)

        loadItemList()

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
                    var acc:Int = 0
                    for (doc in task.result!!) {
                        acc++
                        val item = doc.toObject<ItemList>(ItemList::class.java)
                        item.id = doc.id
                        item.username = doc.get("Username").toString()
                        item.name = doc.get("Name").toString()
                        item.password = doc.get("Password").toString()
                        item.phoneNo = doc.get("Phone Number").toString()
                        item.accType = doc.get("Type").toString()
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
