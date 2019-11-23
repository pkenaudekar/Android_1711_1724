package com.example.emenu.adapter


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.emenu.AccSetActivity
import com.example.emenu.R
import com.example.emenu.data.model.ItemList
import com.google.firebase.firestore.FirebaseFirestore


/*
class ListAdapter(options: FirestoreRecyclerOptions<ItemList>) :
    FirestoreRecyclerAdapter<LauncherActivity.ListItem, ListAdapter.ListHolder>(options) {
    override fun onBindViewHolder(holder: ListHolder?, position: Int, model: LauncherActivity.ListItem?) {
        holder.textViewUsername.text = model.username
        holder.textViewName.text = model.name
        holder.textViewPassword.text = model.password
        holder.textViewPhoneNum.text = model.phoneNo
        holder.spinnerTypeOfAcc.text = model.accType
        holder.itemNumber.text = model.itemNumber.toString()
    }

    class ListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewUsername: TextView = itemView.findViewById(R.id.text_username)
        val textViewName: TextView = itemView.findViewById(R.id.text_name)
        val textViewPassword: TextView = itemView.findViewById(R.id.text_password)
        val textViewPhoneNum: TextView = itemView.findViewById(R.id.text_phone)
        val spinnerTypeOfAcc:TextView = itemView.findViewById(R.id.accType)
        val itemNumber:TextView = itemView.findViewById(R.id.itemNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        return ListHolder(v)
    }
}
*/
class ListAdapter (private val itemList: MutableList<ItemList>,
                       private val context : Context,
                       private val db: FirebaseFirestore): RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.username.text = item.username

        holder.name.text = item.name
        holder.password.text = item.password
        holder.phoneNo.text = item.phoneNo
        holder.accType.text = item.accType
        holder.itemNumber.text = item.itemNumber
        holder.docNumber.text = item.docId
        //Log.d("document",item.docId.toString())
    }

    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.list_items, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        Log.d("itemlist",item.toString())
        holder!!.username.text = item.username
        Log.d("username",item.username.toString())
        holder.name.text = item.name
        holder.password.text = item.password
        holder.phoneNo.text = item.phoneNo
        holder.accType.text = item.accType
        //holder.itemNumber.text = item.itemNumber.toString()


       //holder.edit.setOnClickListener { updateNote(item) }
      // holder.delete.setOnClickListener { deleteNote(item.id!!, position) }
    }

    override fun getItemCount(): Int {

        return itemList.size
    }

*/

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var username: TextView = view.findViewById(R.id.text_username)
        internal var name: TextView = view.findViewById(R.id.text_name)
        internal var password: TextView = view.findViewById(R.id.text_password)
        internal var phoneNo: TextView = view.findViewById(R.id.text_phone)
        internal var accType: TextView = view.findViewById(R.id.text_acc_type)
        internal var itemNumber: TextView = view.findViewById(R.id.list_item_number)
        internal var docNumber: TextView = view.findViewById(R.id.doc_number)

        /*init {


            //edit = view.findViewById(R.id.ivEdit)
            //delete = view.findViewById(R.id.ivDelete)
        }*/
    }


/*
    private fun updateList(item: ItemList) {
        val intent = Intent(context, AccSetActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("Username", item.username)
        intent.putExtra("Password", item.password)
        intent.putExtra("Phone Number", item.phoneNo)
        intent.putExtra("Type", item.accType)
        context.startActivity(intent)
    }*/

    fun removeAt(id: String, position: Int) {

        db.collection("LoginAccount")
            .document(id)
            .delete()
            .addOnCompleteListener {
                itemList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemList.size)
                Toast.makeText(context, "Item has been deleted!", Toast.LENGTH_SHORT).show()
            }
    }
}





