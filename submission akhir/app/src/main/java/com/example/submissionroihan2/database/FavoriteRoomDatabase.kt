package com.example.submissionroihan2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteEntity::class],
    version = 5,
    exportSchema = false
)
abstract class FavoriteRoomDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private var INSTANCE : FavoriteRoomDatabase? = null

        fun getDatabase(context: Context): FavoriteRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FavoriteRoomDatabase::class.java, "favorite_db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as FavoriteRoomDatabase
        }
    }
}