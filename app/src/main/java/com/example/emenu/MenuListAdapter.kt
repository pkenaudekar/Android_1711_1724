package com.example.emenu

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emenu.data.MenuItem
import com.google.firebase.firestore.FirebaseFirestore

class MenuListAdapter (private val menuList: MutableList<MenuItem>,
                      private val context : Context,
                      private val firestoreDB: FirebaseFirestore): RecyclerView.Adapter<MenuListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = menuList[position]
        holder.menuName.text = list.menuName
        holder.price.text =  list.price

        holder.edit.setOnClickListener{updateMenuList(list)}
        //holder.delete.setOnClickListener { deleteMenuList(list.id!!, position) }
    }

    private fun updateMenuList(list: MenuItem) {
        val intent = Intent(context, AddMenuActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("UpdateNoteId", list.id)
        intent.putExtra("UpdateNoteTitle", list.menuName)
        intent.putExtra("UpdateNoteContent", list.price)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent!!.context).inflate(R.layout.activity_menulist, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var menuName: TextView = view.findViewById(R.id.tvTitle)
        internal var price: TextView = view.findViewById(R.id.tvContent)
        internal var edit: ImageView = view.findViewById(R.id.ivEdit)
        internal var delete: ImageView = view.findViewById(R.id.ivDelete)

    }


}