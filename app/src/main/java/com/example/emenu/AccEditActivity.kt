package com.example.emenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_acc_edit.*
import kotlinx.android.synthetic.main.activity_acc_set.btnBack
import kotlinx.android.synthetic.main.activity_acc_set.btnReset

class AccEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acc_edit)

        var first = true
        val typeArray = arrayOf("Type of Account", "Administrative", "Non-Administrative")
        var name = findViewById<EditText>(R.id.editTextName)
        val username = findViewById<EditText>(R.id.editTextUsername)
        val password = findViewById<EditText>(R.id.editTextPass)
        val phone = findViewById<EditText>(R.id.editTextPhone)
        val spinner = findViewById<Spinner>(R.id.spinnerAdminType)
        val db = FirebaseFirestore.getInstance()

        //name.text = intent.getStringExtra("Name").toString()

        if (spinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, typeArray)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {

                    if (first) {
                        first = false
                    } else {

                        if (position == 0) {
                            Toast.makeText(
                                this@AccEditActivity,
                                "Please select appropriate option!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                    Toast.makeText(
                        this@AccEditActivity,
                        "Please select appropriate option in Type od Account!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        btnUpdateAcc.setOnClickListener {
            val s = "MyMessage"

            val account = hashMapOf(
                "Name" to name.text.toString(),
                "Password" to password.text.toString(),
                "Phone Number" to phone.text.toString(),
                "Type" to spinner.selectedItem.toString(),
                "Username" to username.text.toString()
            )

            // Add a new document with a generated ID
            db.collection("LoginAccount")
                .add(account as Map<String, Any>)
                .addOnSuccessListener { documentReference ->
                    Log.d(s, "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(
                        this@AccEditActivity,
                        "New account added successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener { e ->
                    Log.w(s, "Error adding document", e)
                    Toast.makeText(
                        this@AccEditActivity,
                        "Account addition failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            name.text.clear()
            username.text.clear()
            password.text.clear()
            phone.text.clear()
            spinner.setSelection(0)
            first = true
        }

        btnReset.setOnClickListener {
            name.text.clear()
            username.text.clear()
            password.text.clear()
            phone.text.clear()
            spinner.setSelection(0)
            first = true
        }

        btnBack.setOnClickListener {
            val intentOptActivity = Intent(this, AccountListActivity::class.java)
            startActivity(intentOptActivity)
            finish()
        }
    }
}
