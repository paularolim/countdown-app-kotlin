package com.paularolim.countdown.utils

import java.time.temporal.ChronoUnit
import java.util.*

fun getDaysUntil(timestamp: Long): Int {
    return ChronoUnit.DAYS.between(Date().toInstant(), Date(timestamp).toInstant()).toInt()
}