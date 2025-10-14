package com.example.spotifyclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.spotifyclone.ui.theme.SpotifyCloneTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import com.example.spotifyclone.screens.TelaCrudMusica
import com.example.spotifyclone.screens.TelaCrudPlaylist
import com.example.spotifyclone.screens.TelaDetalhesPlaylist

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpotifyCloneApp()
        }
    }
}

@Composable
fun SpotifyCloneApp() {
    val navController = rememberNavController()
    
    SpotifyCloneTheme {
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") { 
                TelaHome(navController = navController) 
            }
            composable("search") { 
                TelaSearch(navController = navController) 
            }
            composable("biblioteca") { 
                TelaBiblioteca(navController = navController) 
            }
            composable("criar") { 
                TelaCriar(navController = navController) 
            }
            composable("crud_musica") {
                TelaCrudMusica(navController = navController)
            }
            composable("crud_playlist") {
                TelaCrudPlaylist(navController = navController)
            }
            composable("playlist_details/{playlistId}") { backStackEntry ->
                val playlistId = backStackEntry.arguments?.getString("playlistId")?.toLongOrNull() ?: 0L
                TelaDetalhesPlaylist(navController = navController, playlistId = playlistId)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaHomePreview() {
    TelaHome()
}
