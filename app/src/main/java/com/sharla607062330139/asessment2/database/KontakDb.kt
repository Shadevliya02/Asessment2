package com.sharla607062330139.asessment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sharla607062330139.asessment2.model.Kontak

@Database(entities = [Kontak::class], version = 2, exportSchema = false)
abstract class KontakDb : RoomDatabase() {

    abstract val dao: KontakDao

    companion object {
        @Volatile
        private var INSTANCE: KontakDb? = null

        fun getInstance(context: Context): KontakDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        KontakDb::class.java,
                        "kontak.db"
                    )
                        .fallbackToDestructiveMigration(false)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
