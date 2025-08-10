package com.example.advice.model.repository

import com.example.advice.model.room.advice.AdviceEntity

interface GetFavoriteAdviceRepository {
    suspend fun getFavoriteAdvice(): List<AdviceEntity>
}