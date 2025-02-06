package com.example.test12.data.local_data_source

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ShoesEntity::class, Onboarding::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun ShoesDao(): ShoesDao
    abstract fun OnBoardingDao(): OnBoardingDao
}