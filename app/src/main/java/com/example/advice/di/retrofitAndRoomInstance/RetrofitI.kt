package com.example.advice.di.retrofitAndRoomInstance

import android.content.Context
import androidx.room.Room
import com.example.advice.model.retrofit.AdviceApiService
import com.example.advice.model.room.advice.AdviceDao
import com.example.advice.model.room.advice.AdviceDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


const val BASE_URL = "https://api.adviceslip.com/"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitAndRoomModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiResponce(retrofit: Retrofit): AdviceApiService {
        return retrofit.create(AdviceApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRoomInst(@ApplicationContext context: Context): AdviceDataBase {
        return Room.databaseBuilder(
                    context = context,
            AdviceDataBase::class.java,
                    name = "advice"
                )
            .build()
    }

    @Provides
    @Singleton
    fun provideAdviceDao(db: AdviceDataBase): AdviceDao {
        return db.adviceDao()
    }

}

