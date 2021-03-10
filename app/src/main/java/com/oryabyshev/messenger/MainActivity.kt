package com.oryabyshev.messenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_register.setOnClickListener {
            val name = text_name_registration.text.toString()
            val email = text_email_registration.text.toString()
            val password = text_password_registration.text.toString()

            if (name.isBlank() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, R.string.error_in_views, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("MainActivity", "Name: $name, Email: $email, Password: $password")

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener

                    Log.d("MainActivity", "Successful registration")

                }
        }

        text_already_have_an_account.setOnClickListener {
            Log.d("MainActivity", "Trying to click - Already have an account")

            //Launch the login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}