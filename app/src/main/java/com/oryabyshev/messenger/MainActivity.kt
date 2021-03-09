package com.oryabyshev.messenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_register.setOnClickListener {
            val name = text_name_registration.text.toString()
            val email = text_email_registration.text.toString()
            val password = text_password_registration.text.toString()

            Log.d("Registration", "Email: $email, Password: $password")
        }
    }
}