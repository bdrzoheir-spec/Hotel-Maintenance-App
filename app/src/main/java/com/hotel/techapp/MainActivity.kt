package com.hotel.techapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hotel.techapp.utils.QrScannerManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnAddNewDevice).setOnClickListener {
            startActivity(Intent(this, AddDeviceActivity::class.java))
        }

        findViewById<Button>(R.id.btnScanQr).setOnClickListener {
            QrScannerManager.startScanning(this) { deviceId ->
                val intent = Intent(this, DeviceDetailsActivity::class.java).apply {
                    putExtra("DEVICE_ID", deviceId)
                }
                startActivity(intent)
            }
        }
    }
}
