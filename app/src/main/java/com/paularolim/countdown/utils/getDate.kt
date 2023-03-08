package com.paularolim.countdown.utils

import java.text.SimpleDateFormat
import java.util.*

fun getDate(s: String): String? {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val netDate = Date(s.toLong())
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}