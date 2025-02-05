package com.example.test12.data.local_data_source

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Entity
data class Onboarding(
    @PrimaryKey val id: Long,
    val number: Int
)

@Dao
interface OnBoardingDao {
    @Insert
    fun insert(onboarding: Onboarding)

    @Query("SELECT number FROM Onboarding WHERE id = 1")
    fun getNumber(): Int

    @Query("UPDATE Onboarding SET number = number+1 WHERE id = 1 ")
    fun updateNumber()
}
