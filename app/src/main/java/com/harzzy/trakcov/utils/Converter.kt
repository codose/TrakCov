package com.harzzy.trakcov.utils

import java.text.SimpleDateFormat

object Converter {
    fun formatDate(updated : Long) : String{
        val sdf = SimpleDateFormat("MMM dd, YYYY HH:mm")
        return sdf.format(updated)
    }
}