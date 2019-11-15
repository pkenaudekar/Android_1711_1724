package com.example.emenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.example.emenu.ui.login.dummy.DummyContent

class MenuSetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)              //change it to activity_menu_set

        //added new stuff
        val addButton : Button = findViewById(R.id.add_button)
        val supportToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        supportToolbar.title = "E-Menu"
        setSupportActionBar(supportToolbar)

        addButton.setOnClickListener {
            val addIntent = Intent(this, AddMenuActivity::class.java).apply {  }
            startActivity(addIntent)
            finish()
        }

    }
}
