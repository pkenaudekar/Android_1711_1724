package com.example.emenu

import android.content.Context
import android.content.Intent
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.emenu.data.MenuItem
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_menulist.view.*

class MenuListAdapter(
    private var menuList: MutableList<MenuItem>,
    private var context: Context,
    private val firestoreDB: FirebaseFirestore
) : RecyclerView.Adapter<MenuListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = menuList[position]
        holder.bind(list, position)
    }

    private fun updateMenuList(list: MenuItem) {
        val intent = Intent(context, AddMenuActivity::class.java)
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
                menuList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, menuList.size)
                Toast.makeText(context, "Item has been deleted!", Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_menulist, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var menuName: TextView = view.findViewById(R.id.tvTitle)
        internal var menuDesc: TextView = view.findViewById(R.id.tvContent)
        internal var price: TextView = view.findViewById(R.id.tvPrice)
        internal var edit: ImageView = view.findViewById(R.id.ivEdit)
        internal var delete: ImageView = view.findViewById(R.id.ivDelete)
        internal var itemImage: ImageView = view.findViewById(R.id.imageView_menuImg)

        fun bind(menuItem: MenuItem, position: Int) {
            menuName.text = menuItem.menuName
            menuDesc.text = menuItem.menuDesc
            price.text = menuItem.menuPrice
            Picasso.get().load(menuItem.imageUrl).into(itemImage)
            Log.d("Stuff", menuItem.menuName!!)

            edit.setOnClickListener { updateMenuList(menuItem) }
            delete.setOnClickListener { deleteMenuList(menuItem.id!!, position) }
        }
    }
}
