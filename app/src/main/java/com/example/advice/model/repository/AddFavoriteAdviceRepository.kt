package com.example.advice.model.repository

interface AddFavoriteAdviceRepository {
    suspend fun addFavoriteAdvice(id: Int,isFavorite: Boolean)
}