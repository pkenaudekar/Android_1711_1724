package com.example.emenu.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.emenu.data.model.ItemList
import com.example.emenu.R
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
        Log.d("username",item.username.toString())
        holder.name.text = item.name
        holder.password.text = item.password
        holder.phoneNo.text = item.phoneNo
        holder.accType.text = item.accType
        holder.itemNumber.text = item.itemNumber

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

        /*init {


            //edit = view.findViewById(R.id.ivEdit)
            //delete = view.findViewById(R.id.ivDelete)
        }*/
    }


/*
    private fun updateList(item: ItemList) {
        val intent = Intent(context, NoteActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("UpdateNoteId", item.id)
        intent.putExtra("UpdateNoteTitle", item.title)
        intent.putExtra("UpdateNoteContent", item.content)
        context.startActivity(intent)
    }

    private fun deleteList(id: String, position: Int) {
        firestoreDB.collection("LoginAccount")
            .document(id)
            .delete()
            .addOnCompleteListener {
                itemList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemList.size)
                Toast.makeText(context, "Item has been deleted!", Toast.LENGTH_SHORT).show()
            }
    }*/


}




