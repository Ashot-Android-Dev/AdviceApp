package com.example.advice.model.repository

import com.example.advice.model.room.advice.AdviceEntity

interface InsertAdviceRepository {
    suspend fun addAdvice(adviceEntity: AdviceEntity)
}