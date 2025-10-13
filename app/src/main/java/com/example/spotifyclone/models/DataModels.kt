package com.example.spotifyclone.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "musicas")
data class Musica(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val artista: String,
    val album: String,
    val duracao: String,
    val imagemUrl: String = ""
)

@Entity(tableName = "playlists")
data class Playlist(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val descricao: String,
    val imagemUrl: String = ""
)

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val email: String
)

@Entity(
    tableName = "playlist_musica_cross_ref",
    primaryKeys = ["playlistId", "musicaId"]
)
data class PlaylistMusicaCrossRef(
    val playlistId: Int,
    val musicaId: Int
)