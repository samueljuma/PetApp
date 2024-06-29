package com.samueljuma.petapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(catEntity: CatEntity)

    @Query("SELECT * FROM Cat")
    fun getCats(): Flow<List<CatEntity>>

    @Update
    suspend fun update(catEntity: CatEntity)

    // Function doesn't use suspend since
    // Flow handles asynchronicity and doesn't block the calling thread
    @Query("SElECT * FROM Cat WHERE isFavorite = 1")
    fun getFavoriteCats(): Flow<List<CatEntity>>
}