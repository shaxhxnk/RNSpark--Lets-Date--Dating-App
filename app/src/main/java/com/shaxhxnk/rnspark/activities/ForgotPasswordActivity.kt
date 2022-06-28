package com.shaxhxnk.rnspark.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shaxhxnk.rnspark.R
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        buttonReset.setOnClickListener {

            if (editTextEmail.text.toString().isNullOrEmpty())

            Toast.makeText(this, "Email Address is not provided", Toast.LENGTH_SHORT).show()

            else {
                auth.sendPasswordResetEmail(
                    editTextEmail.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            Toast.makeText(this, "Reset Password Link is sent", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else
                            Toast.makeText(this, "Password Reset mail could not be sent. Please Enter Valid Email", Toast.LENGTH_LONG).show()

                    }
            }
        }
    }
}