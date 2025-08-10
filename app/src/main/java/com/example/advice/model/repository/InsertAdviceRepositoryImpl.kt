package com.example.advice.model.repository

import com.example.advice.model.room.advice.AdviceDao
import com.example.advice.model.room.advice.AdviceEntity
import javax.inject.Inject

class InsertAdviceRepositoryImpl @Inject constructor(private val adviceDao: AdviceDao) : InsertAdviceRepository {
    override suspend fun addAdvice(adviceEntity: AdviceEntity) = adviceDao.addAdvice(adviceEntity)
}