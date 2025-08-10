package com.example.advice.model.repository

import com.example.advice.model.room.advice.AdviceEntity

interface GetAllAdviceRepository {
    suspend fun getAllAdvice(): List<AdviceEntity>
}