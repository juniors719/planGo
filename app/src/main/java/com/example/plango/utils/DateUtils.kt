package com.example.plango.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

// Converte Timestamp para String formatada
fun Timestamp?.formatToDate(): String {
    return this?.toDate()?.let {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
    } ?: "DD/MM/YYYY"
}