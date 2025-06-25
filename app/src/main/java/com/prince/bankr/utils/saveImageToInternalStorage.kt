package com.prince.bankr.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
    val inputStream = context.contentResolver.openInputStream(uri)
    val filename = "receipt_${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, filename)
    inputStream?.use { input ->
        FileOutputStream(file).use { output ->
            input.copyTo(output)
        }
    }
    return file.absolutePath
}
