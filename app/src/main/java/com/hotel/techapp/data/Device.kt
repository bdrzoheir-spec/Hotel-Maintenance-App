package com.hotel.techapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "devices")
data class Device(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String,
    val pdfUri: String?, 
    val historyFaults: String
)
Utilisez le code avec précaution
