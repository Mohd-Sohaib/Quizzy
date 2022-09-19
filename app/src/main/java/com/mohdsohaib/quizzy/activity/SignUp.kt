package com.mohdsohaib.quizzy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mohdsohaib.quizzy.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.edtEmailAddress
import kotlinx.android.synthetic.main.activity_sign_up.edtPassword

class SignUp : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener{
            signUpUser()
        }

        btn_text_to_login.setOnClickListener {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUpUser (){
        val email = edtEmailAddress.text.toString()
        val password = edtPassword.text.toString()
        val confirmPassword = edtConfirmPassword.text.toString()

        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            Toast.makeText(this,"Email and Password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword){
            Toast.makeText(this,"Password amd Confirm Password do not match",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful){
                    Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LogIn::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"Error creating user",Toast.LENGTH_SHORT).show()
                }
            }
    }
}