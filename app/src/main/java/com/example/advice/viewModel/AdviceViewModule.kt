package com.example.advice.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.advice.model.repository.AddFavoriteAdviceRepository
import com.example.advice.model.repository.DeleteAdviceRepository
import com.example.advice.model.repository.DeleteAllAdviceRepository
import com.example.advice.model.repository.GetAllAdviceRepository
import com.example.advice.model.repository.GetFavoriteAdviceRepository
import com.example.advice.model.repository.InsertAdviceRepository
import com.example.advice.model.retrofit.AdviceApiService
import com.example.advice.model.room.advice.AdviceEntity
import com.example.advice.model.room.advice.toEntity
import com.example.advice.view.uiStates.AdviceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class AdviceViewModule @Inject constructor(
    private val insertAdviceRepository: InsertAdviceRepository,
    private val deleteAdviceRepository: DeleteAdviceRepository,
    private val deleteAllAdviceRepository: DeleteAllAdviceRepository,
    private val getAllAdviceRepository: GetAllAdviceRepository,
    private val apiService: AdviceApiService,
    private val addFavoriteAdviceRepository: AddFavoriteAdviceRepository,
    private val getFavoriteAdviceRepository: GetFavoriteAdviceRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<AdviceState>(AdviceState.Loading)
    val uiState: StateFlow<AdviceState> = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _favorites = MutableStateFlow<List<AdviceEntity>>(emptyList())
    val favorite: StateFlow<List<AdviceEntity>> = _favorites.asStateFlow()

    private val _advice = MutableStateFlow<List<AdviceEntity>>(emptyList())
    val advice: StateFlow<List<AdviceEntity>> = _advice.asStateFlow()

    init {
        fetchAdviceFromDb()
        loadFavoriteAdvices()
    }

    fun loadAdvise() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = AdviceState.Loading
            try {
                val response = apiService.getRandomAdvice()
                val newEntity = response.slip.toEntity(getCurrentData(), isFavorite = false)
                insertAdviceRepository.addAdvice(newEntity)
                fetchAdviceFromDb()
                _advice.value = _advice.value + newEntity

            } catch (e: Exception) {
                _uiState.value = AdviceState.Error(e.message ?: "Error")
            }
        }
    }

    fun fetchAdviceFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val list = getAllAdviceRepository.getAllAdvice()
                _uiState.value = AdviceState.Success(list)
            }catch (e: Exception){
                _uiState.value= AdviceState.Error(e.message?:"Error")
            }
        }
    }

    fun deleteAllAdvice() {
        viewModelScope.launch {
            deleteAllAdviceRepository.deleteAllAdvice()
            _advice.value = emptyList()
        }
    }

    fun deleteAdvice(adviceEntity: AdviceEntity) {
        viewModelScope.launch {
            deleteAdviceRepository.deleteAdvice(adviceEntity)
            _advice.value = _advice.value.filterNot { it.id == adviceEntity.id }
        }
    }

    fun addFavAdvice(adviceEntity: AdviceEntity) {
        viewModelScope.launch {
            addFavoriteAdviceRepository.addFavoriteAdvice(adviceEntity.id, !adviceEntity.isFavorite)
            fetchAdviceFromDb()
            loadFavoriteAdvices()
        }
    }

    fun loadFavoriteAdvices() {
        viewModelScope.launch {
            _favorites.value = getFavoriteAdviceRepository.getFavoriteAdvice()
        }
    }

    fun getCurrentData(): String {
        return SimpleDateFormat("<<dd.MM.yyyy HH:mm>>", Locale.getDefault()).format(Date())
    }
}