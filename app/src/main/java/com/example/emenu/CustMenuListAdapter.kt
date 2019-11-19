package com.example.emenu

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
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

    private fun updateMenuList(list: MenuItem) {
        val intent = Intent(context, CustMenuActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("UpdateMenuId", list.id)
        intent.putExtra("UpdateMenuName", list.menuName)
        intent.putExtra("UpdateMenuDesc", list.menuDesc)
        intent.putExtra("UpdateMenuPrice", list.menuPrice)
        context.startActivity(intent)
    }

    private fun deleteMenuList(id: String, position: Int) {
        firestoreDB.collection("MenuItems")
            .document(id)
            .delete()
            .addOnCompleteListener {
                custmenuList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, custmenuList.size)
                Toast.makeText(context, "Item has been deleted!", Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount(): Int {
        return custmenuList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustMenuListAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_cust_menulist, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var menuName: TextView = view.findViewById(R.id.tvTitle)
        internal var menuDesc: TextView = view.findViewById(R.id.tvContent)
        internal var price: TextView = view.findViewById(R.id.tvPrice)
        internal var orderAdd: ImageView = view.findViewById(R.id.ivAdd)
        internal var orderNum: TextView = view.findViewById(R.id.tv_itemCount)
        internal var orderSub: ImageView = view.findViewById(R.id.ivSubtract)
        internal var itemImage: ImageView = view.findViewById(R.id.imageView_menuImg)

        fun bind(menuItem: MenuItem, position: Int) {
            menuName.text = menuItem.menuName
            menuDesc.text = menuItem.menuDesc
            price.text = menuItem.menuPrice
            Picasso.get().load(menuItem.imageUrl).into(itemImage)
            Log.d("Stuff", menuItem.menuName!!)

            orderAdd.setOnClickListener { updateMenuList(menuItem) }
            orderSub.setOnClickListener { deleteMenuList(menuItem.id!!, position) }
        }
    }

}