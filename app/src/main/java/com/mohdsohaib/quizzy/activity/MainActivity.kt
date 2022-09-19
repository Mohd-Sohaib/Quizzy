package com.mohdsohaib.quizzy.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import com.mohdsohaib.quizzy.R
import com.mohdsohaib.quizzy.adapters.QuizAdapter
import com.mohdsohaib.quizzy.models.Quiz
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter
    private var quizlist = mutableListOf<Quiz>()
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViews()
    }

    private fun setUpViews(){
        setUpFirestore()
        setUpDrawerLayout()
        setUpRecyclerView()
        setUpDatePicker()

    }

    @SuppressLint("SimpleDateFormat")
    private fun setUpDatePicker() {
         btn_date_picker.setOnClickListener {
             val datePicker = MaterialDatePicker.Builder.datePicker().build()
             datePicker.show(supportFragmentManager,"DatePicker")
             datePicker.addOnPositiveButtonClickListener {
                 Log.d("DATEPICKER", datePicker.headerText)
                 val dateFormatter = SimpleDateFormat("dd-mm-yyyy")
                 val date = dateFormatter.format(Date(it))
                 val intent = Intent(this,QuestionsActivity::class.java)
                 intent.putExtra("DATE",date)
                 startActivity(intent)
             }
             datePicker.addOnNegativeButtonClickListener {
                 Log.d("DATEPICKER", datePicker.headerText)
             }
             datePicker.addOnCancelListener {
                 Log.d("DATEPICKER", "Date Picker cancelled")
             }
         }
    }

    private fun setUpFirestore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if(value == null || error != null){
                Toast.makeText(this,"Error fetching data",Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA",value.toObjects(Quiz::class.java).toString())
            quizlist.clear()
            quizlist.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

//    private fun populateDummyData() {
//        quizlist.add(Quiz("10-09-2022","10-09-2022"))
//        quizlist.add(Quiz("11-09-2022","11-09-2022"))
//        quizlist.add(Quiz("12-09-2022","12-09-2022"))
//        quizlist.add(Quiz("13-09-2022","13-09-2022"))
//        quizlist.add(Quiz("14-09-2022","14-09-2022"))
//        quizlist.add(Quiz("10-09-2022","10-09-2022"))
//        quizlist.add(Quiz("11-09-2022","11-09-2022"))
//        quizlist.add(Quiz("12-09-2022","12-09-2022"))
//        quizlist.add(Quiz("13-09-2022","13-09-2022"))
//        quizlist.add(Quiz("14-09-2022","14-09-2022"))
//        quizlist.add(Quiz("10-09-2022","10-09-2022"))
//        quizlist.add(Quiz("11-09-2022","11-09-2022"))
//        quizlist.add(Quiz("12-09-2022","12-09-2022"))
//        quizlist.add(Quiz("13-09-2022","13-09-2022"))
//        quizlist.add(Quiz("14-09-2022","14-09-2022"))
//    }

    private fun setUpRecyclerView() {
         adapter = QuizAdapter(this,quizlist)
        quizRecyclerView.layoutManager = GridLayoutManager(this,2)
        quizRecyclerView.adapter = adapter
    }

    private fun setUpDrawerLayout(){
        setSupportActionBar(appBar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, mainDrawer, R.string.app_name, R.string.app_name)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            mainDrawer.closeDrawers()
            finish()
            true
            
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}