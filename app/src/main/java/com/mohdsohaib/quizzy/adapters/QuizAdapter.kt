package com.mohdsohaib.quizzy.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.session.MediaSession
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mohdsohaib.quizzy.R
import com.mohdsohaib.quizzy.activity.QuestionsActivity
import com.mohdsohaib.quizzy.models.Quiz
import com.mohdsohaib.quizzy.utils.ColorPicker
import com.mohdsohaib.quizzy.utils.IconPicker
import kotlinx.android.synthetic.main.quiz_item.view.*

class QuizAdapter(private val context: Context, private val quizzes: List<Quiz>) :
    RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.quiz_item,parent,false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.textViewTitle.text = quizzes[position].title
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()))
        holder.imageView.setImageResource(IconPicker.getIcon())
        holder.itemView.setOnClickListener {
            Toast.makeText(context,quizzes[position].title,Toast.LENGTH_SHORT).show()
            val intent = Intent(context,QuestionsActivity::class.java)
            intent.putExtra("DATE",quizzes[position].title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    inner class QuizViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var textViewTitle : TextView = itemView.quizTitle
        var imageView : ImageView = itemView.quizIcon
        var cardContainer : CardView = itemView.cardContainer
    }
}