package com.example.spotifyclone.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    var musicaEditando by remember { mutableStateOf<Musica?>(null) }
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
                onClick = { 
                    musicaEditando = null
                    nomeMusica = ""
                    artistaMusica = ""
                    showDialog = true 
                },
                containerColor = SpotifyPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar")
            }
        }
    ) { paddingValues ->
        if (musicas.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "🎵", fontSize = 64.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Nenhuma música ainda",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = SpotifyTextPrimary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Adicione músicas tocando no botão +",
                    fontSize = 14.sp,
                    color = SpotifyTextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        } else {
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
                            Column(modifier = Modifier.weight(1f)) {
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
                            
                            Row {
                                IconButton(onClick = { 
                                    musicaEditando = musica
                                    nomeMusica = musica.titulo
                                    artistaMusica = musica.artista
                                    showDialog = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = SpotifyPrimary)
                                }
                                
                                IconButton(onClick = { viewModel.deleteMusica(musica) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Deletar", tint = SpotifyError)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(if (musicaEditando == null) "Adicionar Música" else "Editar Música", color = SpotifyTextPrimary) },
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
                            if (musicaEditando == null) {
                                // CREATE
                                viewModel.insertMusica(
                                    Musica(
                                        titulo = nomeMusica,
                                        artista = artistaMusica,
                                        album = "",
                                        duracao = ""
                                    )
                                )
                            } else {
                                // UPDATE
                                viewModel.updateMusica(
                                    musicaEditando!!.copy(
                                        titulo = nomeMusica,
                                        artista = artistaMusica
                                    )
                                )
                            }
                            showDialog = false
                        }
                    }
                ) {
                    Text(if (musicaEditando == null) "Adicionar" else "Salvar", color = SpotifyPrimary)
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
