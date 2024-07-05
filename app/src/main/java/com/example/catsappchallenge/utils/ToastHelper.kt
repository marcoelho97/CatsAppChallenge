package com.example.catsappchallenge.utils

import android.content.Context
import android.widget.Toast

fun toastMessage(context: Context, message: Int) {
    Toast.makeText(context, context.getString(message), Toast.LENGTH_SHORT).show()
}