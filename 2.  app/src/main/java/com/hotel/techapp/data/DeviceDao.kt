kotlin
package com.hotel.techapp.data

import androidx.room.*

@Dao
interface DeviceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(device: Device): Long

    @Query("SELECT * FROM devices WHERE id = :deviceId")
    suspend fun getDeviceById(deviceId: Int): Device?

    @Query("SELECT * FROM devices")
    suspend fun getAllDevices(): List<Device>
}
