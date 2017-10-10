package com.example.developer.notes.extension

import java.text.SimpleDateFormat
import java.util.*

val MMM_D_YYYY_HH_MM = "MMM dd yyyy, HH:mm"

fun Date.toString(format: String) = try {
    SimpleDateFormat(format, Locale.ENGLISH).format(this)
} catch (ex: Exception) {
    null
}