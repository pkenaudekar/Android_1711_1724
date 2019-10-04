package com.example.emenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emenu.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLoginPage.setOnClickListener {
            val intent_login = Intent(this, LoginActivity::class.java)
            startActivity(intent_login)
            finish()
        }

        btnMenuSet.setOnClickListener {
            val intent_menue = Intent(this, MenuActivity::class.java)
            startActivity(intent_menue)
            finish()
        }

    }
}
