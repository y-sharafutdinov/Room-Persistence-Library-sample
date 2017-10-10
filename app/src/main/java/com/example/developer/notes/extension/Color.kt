package com.example.developer.notes.extension

import android.graphics.Color

fun Int.darkerColor(): Int {
    val hsv = FloatArray(3)
    Color.colorToHSV(this, hsv)
    hsv[1] = hsv[1] + 0.1f
    hsv[2] = hsv[2] - 0.1f
    return Color.HSVToColor(hsv)
}