package com.example.advice.model.repository

data class AdviceResponseDto(
    val slip: AdviceDto
)

data class AdviceDto(
    val id: Int,
    val advice: String
)


