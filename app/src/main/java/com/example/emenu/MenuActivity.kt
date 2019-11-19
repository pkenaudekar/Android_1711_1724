package com.example.emenu

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.emenu.data.MenuItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_menu.*
import kotlinx.android.synthetic.main.activity_menu_set.*

class MenuActivity : AppCompatActivity() {

    private val TAG = "AddItemActivity"
    private var firestoreDB: FirebaseFirestore? = null
    internal var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        firestoreDB = FirebaseFirestore.getInstance()

        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getString("UpdateMenuId").toString()

            etMenuName.setText(bundle.getString("UpdateMenuName"))
            et_menuprice.setText(bundle.getString("UpdateMenuPrice"))
        }

        button_addMenu.setOnClickListener {
            val menuName: String = etMenuName.text.toString()
            val menuPrice: String = et_menuprice.text.toString()

            if (menuName.isNotEmpty()) {
                if (id.isNotEmpty()) {
                    updateMenu(id, menuName, menuPrice)
                } else {
                    // addMenu(title, menuPrice)
                }
            }
            finish()
        }
    }

    private fun updateMenu(id: String, menuName: String, price: String) {
        val list = MenuItem(id, menuName, price).toMap()

        firestoreDB!!.collection("MenuItems")
            .document(id)
            .set(list)
            .addOnSuccessListener {
                Log.e(TAG, "Note document update successful!")
                Toast.makeText(applicationContext, "Menu has been updated!", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding Note document", e)
                Toast.makeText(applicationContext, "Menu could not be updated!", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}