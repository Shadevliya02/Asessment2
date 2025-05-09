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

    @Query("SELECT * FROM kontak WHERE isDeleted = 0 ORDER BY nama ASC")
    fun getKontak(): Flow<List<Kontak>>

    @Query("SELECT * FROM kontak WHERE id = :id")
    suspend fun getKontakById(id: Long): Kontak?

    @Query("DELETE FROM kontak WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE kontak SET isDeleted = 1 WHERE id = :id")
    suspend fun softDeleteById(id: Long)

    @Query("UPDATE kontak SET isDeleted = 0 WHERE id = :id")
    suspend fun restoreById(id: Long)

    @Query("SELECT * FROM kontak WHERE isDeleted = 1 ORDER BY nama ASC")
    fun getDeletedKontak(): Flow<List<Kontak>>

    @Query("DELETE FROM kontak WHERE isDeleted = 1")
    suspend fun permanentlyDeleteAll()
}