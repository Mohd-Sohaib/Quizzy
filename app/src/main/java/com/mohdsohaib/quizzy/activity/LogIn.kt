package com.mohdsohaib.quizzy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mohdsohaib.quizzy.R
import kotlinx.android.synthetic.main.activity_log_in.*

class LogIn : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        firebaseAuth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener{
            logIn()
        }

        btn_text_to_signup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun logIn(){
        val email = edtEmailAddress.text.toString()
        val password = edtPassword.text.toString()

        if(email.isBlank() || password.isBlank()){
            Toast.makeText(this,"Email/Password cannot be empty",Toast.LENGTH_SHORT).show()
            return
        }
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this,"Authentication error",Toast.LENGTH_SHORT).show()
                }
            }
    }
}