package com.example.emenu

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_add_menu.*
import java.util.*


class AddMenuActivity : AppCompatActivity()  {

    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private lateinit var documentId: String
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)

        var progressbar = findViewById<ProgressBar>(R.id.progressBar)

        //added new stuff
        val supportToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        supportToolbar.title = "Manage Menu"
        setSupportActionBar(supportToolbar)

        val addMenu =findViewById<Button>(R.id.button_addMenu)
        val menu_name = findViewById<EditText>(R.id.etMenuName)
        val menu_desc = findViewById<EditText>(R.id.et_menu_desc)
        val menu_price = findViewById<EditText>(R.id.et_menuprice)
        storageReference = FirebaseStorage.getInstance().reference

        //BUTTON CLICK
        btn_choose_image.setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }

        button_back.setOnClickListener{
            val addIntent = Intent(this, MenuSetActivity::class.java).apply {  }
            startActivity(addIntent)
            finish()
        }

        addMenu.setOnClickListener {
            progressbar.setVisibility(VISIBLE)
            val s = "Adding Menu"
                val menuItem = hashMapOf<String, Any>(
                    "menuName" to menu_name.text.toString(),
                    "menuDesc" to menu_desc.text.toString(),
                    "menuPrice" to menu_price.text.toString()
                )

                db.collection("MenuItems")
                    .add(menuItem as Map<String, Any>)
                    .addOnSuccessListener { documentReference ->
                        Log.d(s, "DocumentSnapshot added with ID: ${documentReference.id}")
                        documentId = documentReference.id
                        Toast.makeText(
                            this@AddMenuActivity,
                            "New Menu Item added successfully.",
                            Toast.LENGTH_SHORT
                        ).show()
                        uploadImage()
                        progressbar.setVisibility(GONE)
                    }
                    .addOnFailureListener { e ->
                        Log.w(s, "Error adding document", e)
                        Toast.makeText(
                            this@AddMenuActivity,
                            "Menu Item addition failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                menu_name.text.clear()
                menu_desc.text.clear()
                menu_price.text.clear()
                image_upload.setImageDrawable(null)
            }

        }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000
        //Permission code
        private val PERMISSION_CODE = 1001
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null && data.getData() != null){
            filePath = data.data
            image_upload.setImageURI(data?.data)
        }
    }

    private fun uploadImage() {
        if (filePath != null) {
            val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })?.addOnCompleteListener{task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    addUploadRecordToDb(downloadUri.toString())
                } else {
                    // Handle failures
                }
            }?.addOnFailureListener{

            }

        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addUploadRecordToDb(uri: String) {
        val menuItem = hashMapOf("imageUrl" to uri)

        db.collection("MenuItems").document(documentId)
            .set(menuItem, SetOptions.merge())
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Saved to DB", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error saving to DB", Toast.LENGTH_LONG).show()
            }
    }
}




