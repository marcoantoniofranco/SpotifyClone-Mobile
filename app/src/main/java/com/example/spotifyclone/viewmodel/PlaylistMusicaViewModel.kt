package com.example.spotifyclone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.spotifyclone.models.Musica
import com.example.spotifyclone.models.Playlist
import com.example.spotifyclone.repository.PlaylistRepository
import com.example.spotifyclone.repository.MusicaRepository
import com.example.spotifyclone.repository.PlaylistMusicaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class PlaylistWithCount(
    val playlist: Playlist,
    val musicCount: Int
)

class PlaylistMusicaViewModel(
    private val playlistRepository: PlaylistRepository,
    private val musicaRepository: MusicaRepository,
    private val playlistMusicaRepository: PlaylistMusicaRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Playlists com contador de músicas
    val playlistsWithCount: StateFlow<List<PlaylistWithCount>> = 
        playlistRepository.getAllPlaylists()
            .map { playlists ->
                playlists.map { playlist ->
                    val count = playlistMusicaRepository.getPlaylistMusicCount(playlist.id)
                    PlaylistWithCount(playlist, count)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    // Todas as músicas disponíveis
    val allMusicas: StateFlow<List<Musica>> = 
        musicaRepository.getAllMusicas()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    // Inserir nova playlist
    fun insertPlaylist(playlist: Playlist) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                playlistRepository.insertPlaylist(playlist)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Atualizar playlist
    fun updatePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                playlistRepository.updatePlaylist(playlist)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Deletar playlist
    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                playlistRepository.deletePlaylist(playlist)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Obter músicas de uma playlist específica
    fun getMusicasFromPlaylist(playlistId: Int): Flow<List<Musica>> {
        return playlistMusicaRepository.getMusicasFromPlaylist(playlistId)
    }

    // Obter playlist por ID
    suspend fun getPlaylistById(playlistId: Long): Playlist? {
        return playlistRepository.getPlaylistById(playlistId.toInt())
    }

    // Adicionar música à playlist
    fun addMusicaToPlaylist(playlistId: Int, musicaId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                playlistMusicaRepository.addMusicaToPlaylist(playlistId, musicaId)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Remover música da playlist
    fun removeMusicaFromPlaylist(playlistId: Int, musicaId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                playlistMusicaRepository.removeMusicaFromPlaylist(playlistId, musicaId)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Verificar se música está na playlist
    suspend fun isMusicaInPlaylist(playlistId: Int, musicaId: Int): Boolean {
        return playlistMusicaRepository.isMusicaInPlaylist(playlistId, musicaId)
    }
}

class PlaylistMusicaViewModelFactory(
    private val playlistRepository: PlaylistRepository,
    private val musicaRepository: MusicaRepository,
    private val playlistMusicaRepository: PlaylistMusicaRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaylistMusicaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaylistMusicaViewModel(playlistRepository, musicaRepository, playlistMusicaRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}