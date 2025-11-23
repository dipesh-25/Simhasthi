package com.example.mahakumbhsafetyapp.ui.screens

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface

fun emojiToBitmap(emoji: String): Bitmap {
    val paint = Paint().apply {
        textSize = 80f
        typeface = Typeface.DEFAULT
        isAntiAlias = true
    }

    val width = paint.measureText(emoji)
    val height = paint.fontSpacing

    val bitmap = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    canvas.drawText(emoji, 0f, height - paint.descent(), paint)

    return bitmap
}
