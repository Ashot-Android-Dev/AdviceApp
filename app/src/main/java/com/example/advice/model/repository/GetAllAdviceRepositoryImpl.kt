package com.example.advice.model.repository

import com.example.advice.model.room.advice.AdviceDao
import com.example.advice.model.room.advice.AdviceEntity
import javax.inject.Inject

class GetAllAdviceRepositoryImpl @Inject constructor(private val adviceDao: AdviceDao): GetAllAdviceRepository {
    override suspend fun getAllAdvice(): List<AdviceEntity> {
        return  adviceDao.getAllAdvice()
    }
}