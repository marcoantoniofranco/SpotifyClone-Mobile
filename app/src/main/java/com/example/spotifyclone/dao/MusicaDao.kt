package com.example.spotifyclone.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.spotifyclone.models.Musica

@Dao
interface MusicaDao {
    
    @Query("SELECT * FROM musicas ORDER BY titulo ASC")
    fun getAllMusicas(): Flow<List<Musica>>

    @Query("SELECT * FROM musicas WHERE id = :id")
    suspend fun getMusicaById(id: Int): Musica?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMusica(musica: Musica): Long
    
    @Update
    suspend fun updateMusica(musica: Musica)
    
    @Delete
    suspend fun deleteMusica(musica: Musica)
    
    @Query("DELETE FROM musicas WHERE id = :id")
    suspend fun deleteMusicaById(id: Int)
}