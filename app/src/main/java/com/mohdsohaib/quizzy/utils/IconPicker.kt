package com.mohdsohaib.quizzy.utils

import com.mohdsohaib.quizzy.R

object IconPicker {
    val icon = arrayOf(
        R.drawable.ic_icon_1,
        R.drawable.ic_icon_2,
        R.drawable.ic_icon_3,
        R.drawable.ic_icon_4,
        R.drawable.ic_icon_5,
        R.drawable.ic_icon_6,
        R.drawable.ic_icon_7,
        R.drawable.ic_icon_8
    )

    var currentIconIndex = 0

    fun getIcon() : Int {
        currentIconIndex = (currentIconIndex + 1) % icon.size
        return icon[currentIconIndex]
    }
}