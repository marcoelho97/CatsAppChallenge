package com.example.catsappchallenge.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

fun toastMessage(context: Context, message: Int, length: Int = Toast.LENGTH_SHORT) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        toast(context, message, length)
    } else {
        Handler(Looper.getMainLooper()).post {
            toast(context, message, length)
        }
    }
}

fun toast(context: Context, message: Int, length: Int) {
    Toast.makeText(context, context.getString(message), length).show()
}