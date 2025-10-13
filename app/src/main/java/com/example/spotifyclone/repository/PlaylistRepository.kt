package com.example.spotifyclone.repository

import com.example.spotifyclone.dao.PlaylistDao
import com.example.spotifyclone.models.Playlist

import kotlinx.coroutines.flow.Flow

class PlaylistRepository(private val playlistDao: PlaylistDao) {
    
    fun getAllPlaylists(): Flow<List<Playlist>> = playlistDao.getAllPlaylists()
    
    suspend fun getPlaylistById(id: Int): Playlist? = playlistDao.getPlaylistById(id)
    
    suspend fun insertPlaylist(playlist: Playlist): Long = playlistDao.insertPlaylist(playlist)
    
    suspend fun updatePlaylist(playlist: Playlist) = playlistDao.updatePlaylist(playlist)
    
    suspend fun deletePlaylist(playlist: Playlist) = playlistDao.deletePlaylist(playlist)
    
    suspend fun deletePlaylistById(id: Int) = playlistDao.deletePlaylistById(id)
}