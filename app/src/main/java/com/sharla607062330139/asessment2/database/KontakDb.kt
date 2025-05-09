package com.sharla607062330139.asessment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sharla607062330139.asessment2.model.Kontak

@Database(entities = [Kontak::class], version = 3, exportSchema = false)
abstract class KontakDb : RoomDatabase() {

    abstract val dao: KontakDao

    companion object {
        @Volatile
        private var INSTANCE: KontakDb? = null

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE kontak ADD COLUMN isDeleted INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getInstance(context: Context): KontakDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        KontakDb::class.java,
                        "kontak.db"
                    )
                        .addMigrations(MIGRATION_2_3)
                        .fallbackToDestructiveMigration(false)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}