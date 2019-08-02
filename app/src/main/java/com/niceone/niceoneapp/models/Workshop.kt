package com.niceone.niceoneapp.models

import java.io.Serializable

class Workshop(
    val id: Int,
    val title: String,
    val logo: String,
    val primary_phone: String,
    val secondry_phone: String,
    val third_phone: String,
    val address: String,
    val lat: String,
    val lon: String
) : Serializable {
}