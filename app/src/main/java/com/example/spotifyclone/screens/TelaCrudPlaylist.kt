package com.example.spotifyclone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spotifyclone.models.Playlist
import com.example.spotifyclone.ui.theme.*
import com.example.spotifyclone.viewmodel.PlaylistMusicaViewModel

// Emojis e cores para as playlists
val playlistEmojis = listOf("🎵", "🎶", "🎤", "🎧", "🎸", "🎹", "🥁", "🎺", "🎷", "🎻")
val playlistGradients = listOf(
    listOf(Color(0xFF1DB954), Color(0xFF1ED760)),
    listOf(Color(0xFFFF6B6B), Color(0xFFFF8E8E)),
    listOf(Color(0xFF4ECDC4), Color(0xFF44A08D)),
    listOf(Color(0xFF667eea), Color(0xFF764ba2)),
    listOf(Color(0xFFf093fb), Color(0xFFf5576c))
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCrudPlaylist(navController: NavController) {
    val viewModel: PlaylistMusicaViewModel = viewModel()
    val playlistsWithCount by viewModel.playlistsWithCount.collectAsState()
    
    var showDialog by remember { mutableStateOf(false) }
    var nomePlaylist by remember { mutableStateOf("") }
    var descricaoPlaylist by remember { mutableStateOf("") }
    
    Scaffold(
        containerColor = SpotifyBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = SpotifyTextPrimary)
                    }
                    Text(
                        text = "Minhas Playlists",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = SpotifyTextPrimary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Text(
                    text = "${playlistsWithCount.size} playlist${if (playlistsWithCount.size != 1) "s" else ""}",
                    fontSize = 14.sp,
                    color = SpotifyTextSecondary
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
        if (playlistsWithCount.isEmpty()) {
            // Tela vazia
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
                    text = "Nenhuma playlist ainda",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = SpotifyTextPrimary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Crie sua primeira playlist tocando no botão +",
                    fontSize = 14.sp,
                    color = SpotifyTextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.padding(paddingValues)
            ) {
                items(playlistsWithCount) { playlistWithCount ->
                    val emojiIndex = playlistWithCount.playlist.id.toInt() % playlistEmojis.size
                    val gradientIndex = playlistWithCount.playlist.id.toInt() % playlistGradients.size
                    
                    PlaylistCardSimple(
                        playlistWithCount = playlistWithCount,
                        emoji = playlistEmojis[emojiIndex],
                        gradient = playlistGradients[gradientIndex],
                        onDelete = { viewModel.deletePlaylist(it) },
                        onClick = { navController.navigate("playlist_details/${it.id}") }
                    )
                }
            }
        }
    }
    
    // Dialog para adicionar playlist
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Nova Playlist", color = SpotifyTextPrimary) },
            text = {
                Column {
                    OutlinedTextField(
                        value = nomePlaylist,
                        onValueChange = { nomePlaylist = it },
                        label = { Text("Nome da Playlist") },
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
                        value = descricaoPlaylist,
                        onValueChange = { descricaoPlaylist = it },
                        label = { Text("Descrição (opcional)") },
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
                        if (nomePlaylist.isNotBlank()) {
                            viewModel.insertPlaylist(
                                Playlist(
                                    nome = nomePlaylist,
                                    descricao = descricaoPlaylist
                                )
                            )
                            nomePlaylist = ""
                            descricaoPlaylist = ""
                            showDialog = false
                        }
                    }
                ) {
                    Text("Criar", color = SpotifyPrimary)
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

@Composable
fun PlaylistCardSimple(
    playlistWithCount: com.example.spotifyclone.viewmodel.PlaylistWithCount,
    emoji: String,
    gradient: List<Color>,
    onDelete: (Playlist) -> Unit,
    onClick: (Playlist) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable { onClick(playlistWithCount.playlist) },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = gradient,
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(1000f, 1000f)
                    )
                )
                .clip(RoundedCornerShape(12.dp))
        ) {
            // Menu de opções
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                IconButton(onClick = { showMenu = true }, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Opções", tint = Color.White)
                }
                
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.background(SpotifyCardBackground)
                ) {
                    DropdownMenuItem(
                        text = { 
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Excluir", color = Color.Red)
                            }
                        },
                        onClick = {
                            showMenu = false
                            onDelete(playlistWithCount.playlist)
                        }
                    )
                }
            }
            
            // Conteúdo do card
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = emoji, fontSize = 48.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                
                Column {
                    Text(
                        text = playlistWithCount.playlist.nome,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    if (playlistWithCount.playlist.descricao.isNotBlank()) {
                        Text(
                            text = playlistWithCount.playlist.descricao,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.White.copy(alpha = 0.7f), modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (playlistWithCount.musicCount == 0) "Vazia" 
                                  else "${playlistWithCount.musicCount} música${if (playlistWithCount.musicCount > 1) "s" else ""}",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}
