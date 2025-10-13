package com.example.spotifyclone.repository

import com.example.spotifyclone.dao.MusicaDao
import com.example.spotifyclone.models.Musica

import kotlinx.coroutines.flow.Flow

class MusicaRepository(private val musicaDao: MusicaDao) {
    
    fun getAllMusicas(): Flow<List<Musica>> = musicaDao.getAllMusicas()
    
    suspend fun getMusicaById(id: Int): Musica? = musicaDao.getMusicaById(id)
    
    suspend fun insertMusica(musica: Musica): Long = musicaDao.insertMusica(musica)
    
    suspend fun updateMusica(musica: Musica) = musicaDao.updateMusica(musica)
    
    suspend fun deleteMusica(musica: Musica) = musicaDao.deleteMusica(musica)
    
    suspend fun deleteMusicaById(id: Int) = musicaDao.deleteMusicaById(id)
}