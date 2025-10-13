package com.example.spotifyclone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.spotifyclone.models.Musica
import com.example.spotifyclone.repository.MusicaRepository

class MusicaViewModel(private val repository: MusicaRepository) : ViewModel() {
    
    private val _musicas = MutableStateFlow<List<Musica>>(emptyList())
    val musicas: StateFlow<List<Musica>> = _musicas.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        viewModelScope.launch {
            repository.getAllMusicas().collect {
                _musicas.value = it
            }
        }
    }
    
    fun insertMusica(musica: Musica) {
        viewModelScope.launch {
            repository.insertMusica(musica)
        }
    }
    
    fun updateMusica(musica: Musica) {
        viewModelScope.launch {
            repository.updateMusica(musica)
        }
    }
    
    fun deleteMusica(musica: Musica) {
        viewModelScope.launch {
            repository.deleteMusica(musica)
        }
    }
}

class MusicaViewModelFactory(private val repository: MusicaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MusicaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MusicaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}