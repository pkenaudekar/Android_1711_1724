package com.example.emenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emenu.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_options.*

class OptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        btnAccountSet.setOnClickListener {
            //val intentAccSet = Intent(this, AccSetActivity::class.java)
            val intentAccList = Intent(this, AccountListActivity::class.java)
            startActivity(intentAccList)
            finish()
        }

        btnCustomerMenu.setOnClickListener {
            val intentMenuSet = Intent(this, MenuSetActivity::class.java)
            startActivity(intentMenuSet)
            finish()
        }

        btnBackAccOpt.setOnClickListener {
            val intentMenuSet = Intent(this, LoginActivity::class.java)
            startActivity(intentMenuSet)
            finish()
        }
    }
}
