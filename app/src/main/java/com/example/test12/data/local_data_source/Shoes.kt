package com.example.test12.data.local_data_source

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class ShoesEntity(
    @PrimaryKey(autoGenerate = true) val shoesId: Long = 0,
    val shoesName: String,
    val shoesUrl: String,
    val shoesDescription: String,
    val shoesCost: Float,
    var shoesCount: Int = 0,
    var shoesInFavourite: Boolean = false
)

@Dao
interface ShoesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg shoes: ShoesEntity)

    @Query("UPDATE ShoesEntity SET shoesInFavourite = NOT shoesInFavourite WHERE shoesId =:shoesId")
    suspend fun changeInFavourite(shoesId: Long)

    @Query("UPDATE ShoesEntity SET shoesCount =:count  WHERE shoesId =:shoesId")
    suspend fun changeInBucket(shoesId: Long, count: Int)

    @Query("UPDATE ShoesEntity SET shoesCount =0  WHERE shoesCount > 0")
    suspend fun resetBucket()

    @Query("SELECT * FROM ShoesEntity")
    suspend fun getAllShoes(): List<ShoesEntity>

    @Query("SELECT * FROM ShoesEntity WHERE shoesInFavourite")
    suspend fun getAllShoesInFavourite(): List<ShoesEntity>

    @Query("SELECT * FROM ShoesEntity WHERE shoesCount > 0")
    suspend fun getAllShoesInBucket(): List<ShoesEntity>
}
