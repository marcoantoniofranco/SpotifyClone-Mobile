package com.example.spotifyclone.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.spotifyclone.models.Playlist

@Dao
interface PlaylistDao {
    
    @Query("SELECT * FROM playlists ORDER BY nome ASC")
    fun getAllPlaylists(): Flow<List<Playlist>>

    @Query("SELECT * FROM playlists WHERE id = :id")
    suspend fun getPlaylistById(id: Int): Playlist?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: Playlist): Long
    
    @Update
    suspend fun updatePlaylist(playlist: Playlist)
    
    @Delete
    suspend fun deletePlaylist(playlist: Playlist)
    
    @Query("DELETE FROM playlists WHERE id = :id")
    suspend fun deletePlaylistById(id: Int)
}