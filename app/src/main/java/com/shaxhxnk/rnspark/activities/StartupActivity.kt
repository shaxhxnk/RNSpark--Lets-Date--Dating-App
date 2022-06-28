package com.shaxhxnk.rnspark.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth


import com.shaxhxnk.rnspark.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.textViewForgotPassword
import kotlinx.android.synthetic.main.activity_startup.*

class StartupActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_startup)
        privacyPolicy.setOnClickListener {
            val url = "https://pages.flycricket.io/rnspark/privacy.html"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)

            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
        }
        privacyPolicy3.setOnClickListener {
            val url = "https://pages.flycricket.io/rnspark/terms.html"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)

            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
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

        startActivity(LoginActivity.newIntent (this))

    }

    fun onSignup(v: View) {

        startActivity(SignupActivity.newIntent(this))
    }

    companion object {
        fun newIntent(context: Context?) = Intent(context, StartupActivity::class.java)
    }
}