package com.mohdsohaib.quizzy.activity

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.mohdsohaib.quizzy.R
import com.mohdsohaib.quizzy.adapters.OptionAdapter
import com.mohdsohaib.quizzy.models.Questions
import com.mohdsohaib.quizzy.models.Quiz
import kotlinx.android.synthetic.main.activity_questions.*

class QuestionsActivity : AppCompatActivity() {

    var quizzes : MutableList<Quiz>? = null
    var questions : MutableMap<String,Questions>? = null
    var index = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        setUpFirestore()
        setUpEventListener()
    }

    private fun setUpEventListener() {
         btnPrevious.setOnClickListener {
             index --
             bindViews()
         }
        btnNext.setOnClickListener {
            index ++
            bindViews()
        }

        btnSubmit.setOnClickListener {
            Log.d("FINAL_RESULT", questions.toString())

            val intent = Intent(this,ResultActivity::class.java)
            val json = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ",json)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpFirestore() {
        val firestore = FirebaseFirestore.getInstance()
        val date = intent.getStringExtra("DATE")
        if(date != null){
            firestore.collection("quizzes").whereEqualTo("title",date)
                .get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty){
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].questions
                        bindViews()
                    }
                }
        }
    }

    private fun bindViews() {
        btnPrevious.visibility = View.GONE
        btnNext.visibility = View.GONE
        btnSubmit.visibility = View.GONE

        when (index) {
            1 -> { //first question
                btnNext.visibility = View.VISIBLE
            }
            questions!!.size -> { //last Question
                btnPrevious.visibility = View.VISIBLE
                btnSubmit.visibility = View.VISIBLE
            }
            else -> { //Middle Question
                btnPrevious.visibility = View.VISIBLE
                btnNext.visibility = View.VISIBLE
            }
        }


//      val questions = Questions(
//          "Our National Animal?",
//          "Cow",
//          "Lion",
//          "Tiger",
//          "Dog",
//          "Tiger"
//      )
        val question = questions!!["question$index"]
        question?.let {
            description_option.text = it.description
            val optionAdapter = OptionAdapter(this,it)
            optionList.layoutManager = LinearLayoutManager(this)
            optionList.adapter = optionAdapter
            optionList.setHasFixedSize(true)
        }
    }
}