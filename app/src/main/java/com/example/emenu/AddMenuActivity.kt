package com.example.emenu

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_acc_set.*

class AddMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        //added new stuff
        val supportToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        supportToolbar.title = "E-Menu"
        setSupportActionBar(supportToolbar)

        val addMenu =findViewById<Button>(R.id.button_addMenu)
        val menu_name = findViewById<EditText>(R.id.etMenuName)
        val menu_desc = findViewById<EditText>(R.id.et_menu_desc)
        val menu_price = findViewById<EditText>(R.id.et_menuprice)
        val db = FirebaseFirestore.getInstance()

        addMenu.setOnClickListener {
            val TAG = "MyMessage"
            // Create a new user with a first and last name
            val menuItem = hashMapOf(
                "Menu Name" to menu_name.text.toString(),
                "Menu Desc" to menu_desc.text.toString(),
                "Menu Price" to menu_price.text.toString()
            )

            db.collection("MenuItems")
                .add(menuItem as Map<String, Any>)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(
                        this@AddMenuActivity,
                        "New Menu Item added successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    Toast.makeText(
                        this@AddMenuActivity,
                        "Menu Item addition failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            menu_name.text.clear()
            menu_desc.text.clear()
            menu_price.text.clear()
        }
    }
}