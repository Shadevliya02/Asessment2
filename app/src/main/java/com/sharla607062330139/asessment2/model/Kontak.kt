package com.sharla607062330139.asessment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kontak")
data class Kontak(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val nomorTelepon: String,
    val gender: String,
    val isDeleted: Boolean = false
)