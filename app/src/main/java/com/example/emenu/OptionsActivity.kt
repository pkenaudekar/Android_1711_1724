package com.example.emenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_options.*

class OptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        btnAccountSet.setOnClickListener {
            val intentAccSet = Intent(this, AccSetActivity::class.java)
            startActivity(intentAccSet)
            finish()
        }

        btnMenuSet.setOnClickListener {
            val intentMenuSet = Intent(this, MenuSetActivity::class.java)
            startActivity(intentMenuSet)
            finish()
        }
    }
}
