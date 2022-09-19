package com.mohdsohaib.quizzy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mohdsohaib.quizzy.R
import kotlinx.android.synthetic.main.activity_login_intro.*

class LoginIntro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_intro)

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null){
            Toast.makeText(this, "User is already logged In!", Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }

        btnGetStarted.setOnClickListener{
            redirect("LOGIN")
        }
    }

    private fun redirect(name : String){
        val intent = when(name) {
            "LOGIN" -> Intent(this, LogIn::class.java)
            "MAIN" -> Intent(this, MainActivity::class.java)
            else -> throw  Exception("No path exists")
        }
        startActivity(intent)
        finish()

        }
}