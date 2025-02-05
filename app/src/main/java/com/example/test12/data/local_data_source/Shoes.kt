package com.example.test12.data.local_data_source

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class Shoes(
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
    @Insert
    suspend fun insertAll(vararg shoes: Shoes)

    @Query("UPDATE Shoes SET shoesInFavourite = NOT shoesInFavourite WHERE shoesId =:shoesId")
    suspend fun changeInFavourite(shoesId: Long)

    @Query("UPDATE Shoes SET shoesCount =:count  WHERE shoesId =:shoesId")
    suspend fun changeInBucket(shoesId: Long, count: Int)

    @Query("UPDATE Shoes SET shoesCount =0  WHERE shoesCount > 0")
    suspend fun resetBucket()

    @Query("SELECT * FROM shoes")
    suspend fun getAllShoes(): List<Shoes>

    @Query("SELECT * FROM shoes WHERE shoesInFavourite")
    suspend fun getAllShoesInFavourite(): List<Shoes>

    @Query("SELECT * FROM shoes WHERE shoesCount > 0")
    suspend fun getAllShoesInBucket(): List<Shoes>
}
