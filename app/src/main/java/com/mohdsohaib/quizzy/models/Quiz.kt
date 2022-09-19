package com.mohdsohaib.quizzy.models

data class Quiz(
    var id : String = "",
    var title : String = "",
    var questions : MutableMap<String,Questions> = mutableMapOf()
    )