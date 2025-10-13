package com.example.spotifyclone.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spotifyclone.models.Musica
import com.example.spotifyclone.viewmodel.MusicaViewModel
import com.example.spotifyclone.viewmodel.MusicaViewModelFactory
import com.example.spotifyclone.repository.MusicaRepository
import com.example.spotifyclone.database.SpotifyDatabase
import com.example.spotifyclone.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCrudMusica(navController: NavController? = null) {
    val context = LocalContext.current
    val database = SpotifyDatabase.getDatabase(context)
    val repository = MusicaRepository(database.musicaDao())
    val viewModel: MusicaViewModel = viewModel(factory = MusicaViewModelFactory(repository))
    
    val musicas by viewModel.musicas.collectAsState()
    
    var showDialog by remember { mutableStateOf(false) }
    var nomeMusica by remember { mutableStateOf("") }
    var artistaMusica by remember { mutableStateOf("") }
    
    Scaffold(
        containerColor = SpotifyBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController?.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = SpotifyTextPrimary)
                }
                Text(
                    text = "Gerenciar Músicas",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = SpotifyTextPrimary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = SpotifyPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(musicas) { musica ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = SpotifyCardBackground)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = musica.titulo,
                                fontWeight = FontWeight.Bold,
                                color = SpotifyTextPrimary
                            )
                            Text(
                                text = musica.artista,
                                fontSize = 14.sp,
                                color = SpotifyTextSecondary
                            )
                        }
                        
                        IconButton(onClick = { viewModel.deleteMusica(musica) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Deletar", tint = SpotifyError)
                        }
                    }
                }
            }
        }
    }
    
    // Dialog para adicionar música
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Adicionar Música", color = SpotifyTextPrimary) },
            text = {
                Column {
                    OutlinedTextField(
                        value = nomeMusica,
                        onValueChange = { nomeMusica = it },
                        label = { Text("Nome da Música") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SpotifyPrimary,
                            focusedLabelColor = SpotifyPrimary,
                            focusedTextColor = SpotifyTextPrimary,
                            unfocusedTextColor = SpotifyTextPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = artistaMusica,
                        onValueChange = { artistaMusica = it },
                        label = { Text("Artista") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SpotifyPrimary,
                            focusedLabelColor = SpotifyPrimary,
                            focusedTextColor = SpotifyTextPrimary,
                            unfocusedTextColor = SpotifyTextPrimary
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (nomeMusica.isNotBlank() && artistaMusica.isNotBlank()) {
                            viewModel.insertMusica(
                                Musica(
                                    titulo = nomeMusica,
                                    artista = artistaMusica,
                                    album = "",
                                    duracao = ""
                                )
                            )
                            nomeMusica = ""
                            artistaMusica = ""
                            showDialog = false
                        }
                    }
                ) {
                    Text("Adicionar", color = SpotifyPrimary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar", color = SpotifyTextSecondary)
                }
            },
            containerColor = SpotifyCardBackground
        )
    }
}
