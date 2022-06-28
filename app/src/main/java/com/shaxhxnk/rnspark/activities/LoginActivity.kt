package com.shaxhxnk.rnspark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Toast


import kotlinx.android.synthetic.main.activity_login.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.shaxhxnk.rnspark.R
import com.shaxhxnk.rnspark.activities.LoginActivity.Companion.newIntent


class LoginActivity : AppCompatActivity() {


    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = firebaseAuth.currentUser
        if(user != null) {
            if(user.isEmailVerified) {
                startActivity(TinderActivity.newIntent(this))
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        textViewForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }

    fun onLogin(v: View) {
        if(!editTextEmail.text.toString().isNullOrEmpty() && !passwordET.text.toString().isNullOrEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(editTextEmail.text.toString(), passwordET.text.toString())
                .addOnCompleteListener { task ->
                    if(!task.isSuccessful) {
                        Toast.makeText(this, "Login error ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }else {
                        if (firebaseAuth.currentUser!!.isEmailVerified) {
                            val intent = Intent(this, InstructionsActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(this, "Please Verify your Email then login", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
        }
    }

    companion object {
        fun newIntent(context: Context?) = Intent(context, LoginActivity::class.java)
    }

}
