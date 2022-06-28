package com.shaxhxnk.rnspark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.shaxhxnk.rnspark.R
import com.shaxhxnk.rnspark.User
import com.shaxhxnk.rnspark.util.DATA_USERS
import kotlinx.android.synthetic.main.activity_signup.*



class SignupActivity : AppCompatActivity() {

    private val firebaseDatabase = FirebaseDatabase.getInstance().reference
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
        setContentView(R.layout.activity_signup)
    }



    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }

    fun onSignup(v: View) {
        if(!editTextEmail.text.toString().isNullOrEmpty() && !passwordET.text.toString().isNullOrEmpty()) {
            firebaseAuth.createUserWithEmailAndPassword(editTextEmail.text.toString(), passwordET.text.toString())
                .addOnCompleteListener { task ->
                    if(!task.isSuccessful) {
                        Toast.makeText(this, "Signup error ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
                    } else {
                        firebaseAuth.currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val email = editTextEmail.text.toString()
                                    val userId = firebaseAuth.currentUser?.uid ?: ""
                                    val user = User(userId, "", "", email, "", "", "")
                                    firebaseDatabase.child(DATA_USERS).child(userId).setValue(user)
                                    Toast.makeText(this, "Sign Up successful. Verification link sent to the Email address", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this, LoginActivity::class.java)
                                    AlertDialog.Builder(this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("Verify Email")
                                        .setMessage("Verification link Sent to your Email Address. Please verify")
                                        .setPositiveButton("OK") { dialog, which ->
                                            startActivity(intent)
                                            finish()
                                        }

                                        .show()

                                }else{
                                    Toast.makeText(this, "Signup error ${task.exception?.localizedMessage}", Toast.LENGTH_LONG).show()

                                }
                    }}
                }
        }
    }

    companion object {
        fun newIntent(context: Context?) = Intent(context, SignupActivity::class.java)
    }
}