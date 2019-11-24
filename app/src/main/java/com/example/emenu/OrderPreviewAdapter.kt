package com.example.emenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.emenu.data.MenuItem
import com.google.firebase.firestore.FirebaseFirestore

class OrderPreviewAdapter (
private var menuList: MutableList<MenuItem>,
private var context: Context,
private val firestoreDB: FirebaseFirestore
) : RecyclerView.Adapter<OrderPreviewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_recyclerview_menubill, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = menuList[position]
        holder.bind(list, position)
    }


    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var menuName: TextView = view.findViewById(R.id.tvSrNo)
        internal var menuDesc: TextView = view.findViewById(R.id.tvBillItemName)
        internal var price: TextView = view.findViewById(R.id.tvBillPrice)
        internal  var quantity : TextView = view.findViewById(R.id.tvBillQty)

        fun bind( menuItem: MenuItem, quantity: Int) {
            menuName.text = menuItem.menuName
            menuDesc.text = menuItem.menuDesc
            price.text = menuItem.menuPrice
        }
    }
}
