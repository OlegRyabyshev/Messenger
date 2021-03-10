package com.oryabyshev.messenger

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_login.setOnClickListener{
            val email = text_email_login.text.toString()
            val password = text_password_login.text.toString()

            Log.d("MainActivity", "Email: $email, Password: $password")
        }

        return_to_registration.setOnClickListener {
            finish();
        }
    }

}