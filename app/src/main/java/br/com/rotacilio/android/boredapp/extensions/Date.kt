package br.com.rotacilio.android.boredapp.extensions

import java.util.*

fun Date.between(end: Date?): Long {
    val timeDifference = end?.time!! - time
    return timeDifference / 1000 / 60
}