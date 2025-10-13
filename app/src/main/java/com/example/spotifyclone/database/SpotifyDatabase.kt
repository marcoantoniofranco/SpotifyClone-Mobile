package com.example.spotifyclone.database

import androidx.room.*
import android.content.Context
import com.example.spotifyclone.models.Musica
import com.example.spotifyclone.models.Playlist
import com.example.spotifyclone.models.PlaylistMusicaCrossRef
import com.example.spotifyclone.dao.MusicaDao
import com.example.spotifyclone.dao.PlaylistDao
import com.example.spotifyclone.dao.PlaylistMusicaDao

@Database(
    entities = [Musica::class, Playlist::class, PlaylistMusicaCrossRef::class],
    version = 2,
    exportSchema = false
)
abstract class SpotifyDatabase : RoomDatabase() {
    abstract fun musicaDao(): MusicaDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun playlistMusicaDao(): PlaylistMusicaDao
    
    companion object {
        @Volatile
        private var INSTANCE: SpotifyDatabase? = null
        
        fun getDatabase(context: Context): SpotifyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SpotifyDatabase::class.java,
                    "spotify_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}