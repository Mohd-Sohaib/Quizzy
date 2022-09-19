package com.mohdsohaib.quizzy.utils

object ColorPicker {
    val color = arrayOf("#ff4000",
        "#ff8000",
        "#ffbf00",
        "#ffff00",
        "#80ff00",
        "#00ff00",
        "#00ff80",
        "#0080ff",
        "#0000ff",
        "#bf00ff",
        "#ff00bf")

    var currentColorIndex = 0

    fun getColor() : String{
        currentColorIndex = (currentColorIndex + 1) % color.size
        return color[currentColorIndex]
    }
}