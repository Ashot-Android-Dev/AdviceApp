package com.example.advice.model.repository

import com.example.advice.model.room.advice.AdviceDao
import javax.inject.Inject

class AddFavoriteAdviceRepositoryImpl @Inject constructor(private val adviceDao: AdviceDao): AddFavoriteAdviceRepository {
    override suspend fun addFavoriteAdvice(id: Int, isFavorite: Boolean) {
        return adviceDao.addFavoriteAdvice(id=id, isFavorite = isFavorite)
    }
}