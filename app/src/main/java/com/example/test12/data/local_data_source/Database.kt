package com.example.test12.data.local_data_source

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ShoesEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun ShoesDao(): ShoesDao
}