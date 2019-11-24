package com.example.emenu

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.emenu.data.MenuItem
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso


class CustMenuListAdapter (
    private var custmenuList: MutableList<MenuItem>,
    private var context: Context,
    private val firestoreDB: FirebaseFirestore
) : RecyclerView.Adapter<CustMenuListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = custmenuList[position]
        holder.bind(list, position)
    }

    private fun addToCart(list: MenuItem,orderNum:TextView) {
        val temp = orderNum.text.toString().toInt() + 1
        orderNum.text = temp.toString()
    }

    private fun deleteFromCart(id: String, position: Int,orderNum:TextView) {

        var temp = orderNum.text.toString().toInt()
        if (temp > 0){
            temp -= 1
            orderNum.text = temp.toString()
        }
    }

    override fun getItemCount(): Int {
        return custmenuList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_cust_menulist, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var menuName: TextView = view.findViewById(R.id.tvTitle)
        internal var menuDesc: TextView = view.findViewById(R.id.tvContent)
        internal var price: TextView = view.findViewById(R.id.tvPrice)
        internal var orderAdd: ImageView = view.findViewById(R.id.ivAdd)
        internal var orderSub: ImageView = view.findViewById(R.id.ivSubtract)
        internal var orderNum: TextView = view.findViewById(R.id.tv_itemCount)
        internal var itemImage: ImageView = view.findViewById(R.id.imageView_menuImg)

        fun bind(menuItem: MenuItem, position: Int) {
            menuName.text = menuItem.menuName
            menuDesc.text = menuItem.menuDesc
            price.text = menuItem.menuPrice
            Picasso.get().load(menuItem.imageUrl).into(itemImage)
            Log.d("Stuff", menuItem.menuName!!)

            orderAdd.setOnClickListener { addToCart(menuItem,orderNum) }
            orderSub.setOnClickListener { deleteFromCart(menuItem.id!!, position,orderNum) }
        }
    }

}