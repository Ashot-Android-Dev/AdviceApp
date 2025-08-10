package com.example.advice.model.repository

import com.example.advice.model.room.advice.AdviceDao
import com.example.advice.model.room.advice.AdviceEntity
import javax.inject.Inject

class DeleteAdviceRepositoryImpl @Inject constructor(private val adviceDao: AdviceDao) : DeleteAdviceRepository {
    override suspend fun deleteAdvice(adviceEntity: AdviceEntity) = adviceDao.deleteAdvice(adviceEntity)
}