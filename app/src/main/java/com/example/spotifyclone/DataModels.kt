package com.example.spotifyclone

data class Musica(
    val id: Int,
    val titulo: String,
    val artista: String,
    val album: String,
    val duracao: String,
    val imagemUrl: String = ""
)

data class Playlist(
    val id: Int,
    val nome: String,
    val descricao: String,
    val musicas: List<Musica> = emptyList(),
    val imagemUrl: String = ""
)

data class Usuario(
    val id: Int,
    val nome: String,
    val email: String,
    val playlists: List<Playlist> = emptyList()
)

// Classe para simular repositório de dados
class MusicRepository {
    companion object {
        fun getMusicasRecentes(): List<Musica> {
            return listOf(
                Musica(1, "OK Computer", "Radiohead", "OK Computer", "3:21"),
                Musica(2, "Song 2", "Blur", "Blur: The Best Of", "2:02"),
                Musica(3, "Govinda", "Kula Shaker", "K", "4:07"),
                Musica(4, "Bitter Sweet Symphony", "The Verve", "Urban Hymns", "5:58"),
                Musica(5, "The Last Dinner Party", "Nothing Matters", "Prelude to Ecstasy", "3:45"),
                Musica(6, "Close to Me", "The Cure", "Disintegration", "3:24")
            )
        }
        
        fun getPlaylistsPopulares(): List<Playlist> {
            return listOf(
                Playlist(1, "Rock Clássico", "Os melhores do rock"),
                Playlist(2, "Indie Hits", "Sucessos independentes"),
                Playlist(3, "Chill Vibes", "Para relaxar")
            )
        }
    }
}