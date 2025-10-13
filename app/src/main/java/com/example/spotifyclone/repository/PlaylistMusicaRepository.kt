package com.example.spotifyclone.repository

import com.example.spotifyclone.dao.PlaylistMusicaDao
import com.example.spotifyclone.models.Musica
import com.example.spotifyclone.models.PlaylistMusicaCrossRef
import kotlinx.coroutines.flow.Flow

class PlaylistMusicaRepository(private val playlistMusicaDao: PlaylistMusicaDao) {
    
    fun getMusicasFromPlaylist(playlistId: Int): Flow<List<Musica>> = 
        playlistMusicaDao.getMusicasFromPlaylist(playlistId)
    
    suspend fun addMusicaToPlaylist(playlistId: Int, musicaId: Int) {
        playlistMusicaDao.insertPlaylistMusica(PlaylistMusicaCrossRef(playlistId, musicaId))
    }
    
    suspend fun removeMusicaFromPlaylist(playlistId: Int, musicaId: Int) {
        playlistMusicaDao.removeMusicaFromPlaylist(playlistId, musicaId)
    }
    
    suspend fun getPlaylistMusicCount(playlistId: Int): Int = 
        playlistMusicaDao.getPlaylistMusicCount(playlistId)
    
    suspend fun isMusicaInPlaylist(playlistId: Int, musicaId: Int): Boolean = 
        playlistMusicaDao.isMusicaInPlaylist(playlistId, musicaId)
}