package com.example.advice.model.repository

import com.example.advice.model.room.advice.AdviceEntity

interface DeleteAdviceRepository {
    suspend fun deleteAdvice(adviceEntity: AdviceEntity)
}