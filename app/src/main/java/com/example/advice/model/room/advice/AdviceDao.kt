package com.example.advice.model.room.advice

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AdviceDao{
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addAdvice(adviceEntity: AdviceEntity)

    @Query("SELECT * FROM advise_Table ORDER BY dataTime DESC")
    suspend fun getAllAdvice(): List<AdviceEntity>

    @Query("SELECT * FROM advise_Table WHERE isFavorite = 1 ORDER BY dataTime DESC")
    suspend fun getFavoriteAdvice(): List<AdviceEntity>

    @Query("UPDATE advise_Table SET isFavorite = :isFavorite WHERE id= :id")
    suspend fun addFavoriteAdvice(isFavorite: Boolean,id: Int)

    @Delete
    suspend fun deleteAdvice(adviceEntity: AdviceEntity)

    @Query("DELETE FROM advise_Table")
    suspend fun deleteAllAdvice()
}