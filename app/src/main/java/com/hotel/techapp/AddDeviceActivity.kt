package com.hotel.techapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hotel.techapp.data.AppDatabase
import com.hotel.techapp.data.Device
import kotlinx.coroutines.launch

class AddDeviceActivity : AppCompatActivity() {
    private var selectedPdfUri: Uri? = null
    private lateinit var tvSelectedPdfPath: TextView

    private val selectPdfLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedPdfUri = uri
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                tvSelectedPdfPath.text = "تم اختيار مانيوال الجهاز بنجاح"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)

        val etName = findViewById<EditText>(R.id.etDeviceName)
        val etType = findViewById<EditText>(R.id.etDeviceType)
        val etFaults = findViewById<EditText>(R.id.etFaultsHistory)
        tvSelectedPdfPath = findViewById(R.id.tvSelectedPdfPath)

        findViewById<Button>(R.id.btnSelectPdf).setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/pdf"
            }
            selectPdfLauncher.launch(intent)
        }

        findViewById<Button>(R.id.btnSaveDevice).setOnClickListener {
            lifecycleScope.launch {
                AppDatabase.getDatabase(this@AddDeviceActivity).deviceDao().insertDevice(
                    Device(name = etName.text.toString(), type = etType.text.toString(), pdfUri = selectedPdfUri?.toString(), historyFaults = etFaults.text.toString())
                )
                Toast.makeText(this@AddDeviceActivity, "تم الحفظ!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
