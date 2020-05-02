package com.harzzy.trakcov.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.util.*

@SuppressLint("NewApi", "SimpleDateFormat")
object Converter {
    fun formatDate(updated : Long) : String{
        val sdf = SimpleDateFormat("MMM dd, YYYY HH:mm")
        return sdf.format(updated)
    }

    fun formatNewsPublishedDate(published : String) : String{
        val instant = Instant.parse(published)
        val date = Date.from(instant)
        val formatter = SimpleDateFormat("MMM dd, YYYY HH:mm")
        return formatter.format(date)
    }
}