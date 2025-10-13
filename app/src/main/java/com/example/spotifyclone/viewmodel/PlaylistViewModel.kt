package com.example.spotifyclone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.spotifyclone.models.Playlist
import com.example.spotifyclone.repository.PlaylistRepository

class PlaylistViewModel(private val repository: PlaylistRepository) : ViewModel() {
    
    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> = _playlists.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    init {
        viewModelScope.launch {
            repository.getAllPlaylists().collect {
                _playlists.value = it
            }
        }
    }
    
    fun insertPlaylist(playlist: Playlist) {
        viewModelScope.launch {
            repository.insertPlaylist(playlist)
        }
    }
    
    fun updatePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            repository.updatePlaylist(playlist)
        }
    }
    
    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            repository.deletePlaylist(playlist)
        }
    }
}

class PlaylistViewModelFactory(private val repository: PlaylistRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaylistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaylistViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}