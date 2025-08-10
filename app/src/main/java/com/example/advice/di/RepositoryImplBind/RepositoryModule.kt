package com.example.advice.di.RepositoryImplBind

import com.example.advice.model.repository.AddFavoriteAdviceRepository
import com.example.advice.model.repository.AddFavoriteAdviceRepositoryImpl
import com.example.advice.model.repository.DeleteAdviceRepository
import com.example.advice.model.repository.DeleteAdviceRepositoryImpl
import com.example.advice.model.repository.DeleteAllAdviceRepository
import com.example.advice.model.repository.DeleteAllAdviceRepositoryImpl
import com.example.advice.model.repository.GetAllAdviceRepository
import com.example.advice.model.repository.GetAllAdviceRepositoryImpl
import com.example.advice.model.repository.GetFavoriteAdviceRepository
import com.example.advice.model.repository.GetFavoriteAdviceRepositoryImpl
import com.example.advice.model.repository.InsertAdviceRepository
import com.example.advice.model.repository.InsertAdviceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAddAdviceRepo(repos: InsertAdviceRepositoryImpl): InsertAdviceRepository

    @Binds
    abstract fun bindDeleteAdviceRepo(repos: DeleteAdviceRepositoryImpl): DeleteAdviceRepository

    @Binds
    abstract fun bindGetAllAdviceRepo(repos: GetAllAdviceRepositoryImpl): GetAllAdviceRepository


    @Binds
    abstract fun bindDeleteAllAdvice(repos: DeleteAllAdviceRepositoryImpl): DeleteAllAdviceRepository

    @Binds
    abstract fun bindAddFavRepo(repos: AddFavoriteAdviceRepositoryImpl): AddFavoriteAdviceRepository

    @Binds
    abstract fun bindGetFavAdvice(repos: GetFavoriteAdviceRepositoryImpl): GetFavoriteAdviceRepository
}