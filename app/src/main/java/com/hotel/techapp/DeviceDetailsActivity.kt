package com.hotel.techapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hotel.techapp.data.AppDatabase
import com.hotel.techapp.utils.QrGeneratorManager
import kotlinx.coroutines.launch

class DeviceDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_details)

        val deviceId = intent.getIntExtra("DEVICE_ID", -1)
        lifecycleScope.launch {
            val device = AppDatabase.getDatabase(this@DeviceDetailsActivity).deviceDao().getDeviceById(deviceId)
            if (device != null) {
                findViewById<TextView>(R.id.tvDetailName).text = device.name
                findViewById<TextView>(R.id.tvDetailType).text = device.type
                findViewById<TextView>(R.id.tvDetailFaults).text = device.historyFaults
                findViewById<ImageView>(R.id.ivGeneratedQr).setImageBitmap(QrGeneratorManager.generateQrCode(device.id.toString()))
                
                findViewById<Button>(R.id.btnOpenPdf).setOnClickListener {
                    if (!device.pdfUri.isNullOrEmpty()) {
                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(Uri.parse(device.pdfUri), "application/pdf")
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        })
                    }
                }
            }
        }
    }
}
