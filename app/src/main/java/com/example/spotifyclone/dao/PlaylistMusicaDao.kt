package com.example.spotifyclone.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.spotifyclone.models.Musica
import com.example.spotifyclone.models.Playlist
import com.example.spotifyclone.models.PlaylistMusicaCrossRef

@Dao
interface PlaylistMusicaDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistMusica(crossRef: PlaylistMusicaCrossRef)
    
    @Delete
    suspend fun deletePlaylistMusica(crossRef: PlaylistMusicaCrossRef)
    
    @Query("SELECT * FROM musicas INNER JOIN playlist_musica_cross_ref ON musicas.id = playlist_musica_cross_ref.musicaId WHERE playlist_musica_cross_ref.playlistId = :playlistId")
    fun getMusicasFromPlaylist(playlistId: Int): Flow<List<Musica>>
    
    @Query("SELECT COUNT(*) FROM playlist_musica_cross_ref WHERE playlistId = :playlistId")
    suspend fun getPlaylistMusicCount(playlistId: Int): Int
    
    @Query("DELETE FROM playlist_musica_cross_ref WHERE playlistId = :playlistId AND musicaId = :musicaId")
    suspend fun removeMusicaFromPlaylist(playlistId: Int, musicaId: Int)
    
    @Query("SELECT EXISTS(SELECT 1 FROM playlist_musica_cross_ref WHERE playlistId = :playlistId AND musicaId = :musicaId)")
    suspend fun isMusicaInPlaylist(playlistId: Int, musicaId: Int): Boolean
}