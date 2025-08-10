package com.example.advice.model.repository

import com.example.advice.model.room.advice.AdviceDao
import javax.inject.Inject

class DeleteAllAdviceRepositoryImpl @Inject constructor(private val adviceDao: AdviceDao): DeleteAllAdviceRepository {
    override suspend fun deleteAllAdvice() = adviceDao.deleteAllAdvice()
}