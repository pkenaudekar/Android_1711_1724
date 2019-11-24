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
    private val firestoreDB: FirebaseFirestore,
    private val eListener: OnMenuItemSelectedListener
) : RecyclerView.Adapter<CustMenuListAdapter.ViewHolder>() {

    interface OnMenuItemSelectedListener{
        fun onAddToCart(menuItem: MenuItem,orderNum: TextView)
        fun onRemoveFromCart(menuItem: MenuItem,orderNum: TextView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = custmenuList[position]
        holder.bind(list, eListener)
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
        private var menuName: TextView = view.findViewById(R.id.tvTitle)
        private var menuDesc: TextView = view.findViewById(R.id.tvContent)
        private var price: TextView = view.findViewById(R.id.tvPrice)
        private var orderAdd: ImageView = view.findViewById(R.id.ivAdd)
        private var orderSub: ImageView = view.findViewById(R.id.ivSubtract)
        private var orderNum: TextView = view.findViewById(R.id.tv_itemCount)
        private var itemImage: ImageView = view.findViewById(R.id.imageView_menuImg)


        fun bind(menuItem: MenuItem, listener: OnMenuItemSelectedListener) {

            menuName.text = menuItem.menuName
            menuDesc.text = menuItem.menuDesc
            price.text = menuItem.menuPrice
            Picasso.get().load(menuItem.imageUrl).into(itemImage)
            Log.d("Stuff", menuItem.menuName!!)

            orderAdd.setOnClickListener { listener.onAddToCart(menuItem,orderNum) }
            orderSub.setOnClickListener { listener.onRemoveFromCart(menuItem,orderNum) }
        }
    }

}