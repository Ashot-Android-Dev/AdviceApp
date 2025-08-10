package com.example.advice.model.retrofit

import com.example.advice.model.repository.AdviceResponseDto
import retrofit2.http.GET

interface AdviceApiService {
    @GET("advice")
    suspend fun getRandomAdvice(): AdviceResponseDto
}