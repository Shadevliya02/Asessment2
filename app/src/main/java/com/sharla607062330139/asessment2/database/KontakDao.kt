package com.sharla607062330139.asessment2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sharla607062330139.asessment2.model.Kontak
import kotlinx.coroutines.flow.Flow

@Dao
interface KontakDao {

    @Insert
    suspend fun insert(kontak: Kontak)

    @Update
    suspend fun update(kontak: Kontak)

    @Query("SELECT * FROM kontak ORDER BY nama ASC")
    fun getKontak(): Flow<List<Kontak>>

    @Query("SELECT * FROM kontak WHERE id = :id")
    suspend fun getKontakById(id: Long): Kontak?

    @Query("DELETE FROM kontak WHERE id = :id")
    suspend fun deleteById(id: Long)
}
