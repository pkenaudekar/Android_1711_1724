package com.example.emenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emenu.ui.login.ItemMenuFragment
import com.example.emenu.ui.login.dummy.DummyContent

class MenuSetActivity : AppCompatActivity(), ItemMenuFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_set)

        //added new stuff
        if(savedInstanceState==null) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.main_container, ItemMenuFragment())
                commit()
            }
        }
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
