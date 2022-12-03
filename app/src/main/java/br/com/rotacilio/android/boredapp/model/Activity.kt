package br.com.rotacilio.android.boredapp.model

data class Activity(
    val activity: String,
    val type: String,
    val participants: Int,
    val price: Double,
    val link: String,
    val key: String,
    val accessibility: Double
)
