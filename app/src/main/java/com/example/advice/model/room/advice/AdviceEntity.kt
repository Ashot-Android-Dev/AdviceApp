package com.example.advice.model.room.advice

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.advice.model.repository.AdviceDto

@Entity(tableName = "advise_Table")
data class AdviceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val advice: String,
    val dataTime: String,
    val isFavorite: Boolean,
)

fun AdviceDto.toEntity(dataTime: String,isFavorite: Boolean): AdviceEntity {
    return AdviceEntity(
        id = this.id,
        advice = this.advice,
        dataTime = dataTime,
        isFavorite = isFavorite
    )
}
