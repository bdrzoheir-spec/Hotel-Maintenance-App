package com.hotel.techapp.utils

import android.content.Context
import android.widget.Toast
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

object QrScannerManager {
    fun startScanning(context: Context, onResult: (Int) -> Unit) {
        val scanner = GmsBarcodeScanning.getClient(context)
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                val qrValue = barcode.rawValue
                val deviceId = qrValue?.toIntOrNull()
                if (deviceId != null) {
                    onResult(deviceId)
                } else {
                    Toast.makeText(context, "كود QR غير صالح", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "فشل مسح الكود", Toast.LENGTH_SHORT).show()
            }
    }
}
