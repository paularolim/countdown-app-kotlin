package com.paularolim.countdown.utils

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

fun getDaysUntil(timestamp: Long): Int {
    val today = Calendar.getInstance()
    today.set(Calendar.HOUR_OF_DAY, 0)
    today.set(Calendar.MINUTE, 0)
    today.set(Calendar.SECOND, 0)
    today.set(Calendar.MILLISECOND, 0)

    val target = Instant.ofEpochMilli(timestamp)

    return ChronoUnit.DAYS.between(today.toInstant(), target).toInt()
}