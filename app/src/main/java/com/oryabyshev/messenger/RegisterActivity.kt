package com.oryabyshev.messenger

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class RegisterActivity : AppCompatActivity() {
    private val TAG = RegisterActivity::class.java.canonicalName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_select_photo.setOnClickListener {
            Log.d(TAG, "Trying to select an image")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        button_register.setOnClickListener {
            performRegister()
        }

        text_already_have_an_account.setOnClickListener {
            Log.d(TAG, "Trying to click - Already have an account")

            //Launch the login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private var selectedPhotoURI: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && requestCode == Activity.RESULT_OK || data != null) {
            Log.d(TAG, "Photo was selected")
            selectedPhotoURI = data?.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoURI)
            val bitmapDrawable = BitmapDrawable(bitmap)
            button_select_photo.background = bitmapDrawable

        }
    }

    private fun performRegister() {
        val name = text_name_registration.text.toString()
        val email = text_email_registration.text.toString()
        val password = text_password_registration.text.toString()

        if (name.isBlank() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.error_in_views, Toast.LENGTH_SHORT).show()
            return
        }

        Log.d(TAG, "Name: $name, Email: $email, Password: $password")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d(TAG, "Successful registration")
                uploadImageToFirebaseStorage()
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to created a new user: ${it.message}")
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoURI == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhotoURI!!)
            .addOnSuccessListener {
                Log.d(TAG, "Uploaded image")
            }
    }
}