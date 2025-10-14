package com.example.spotifyclone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spotifyclone.models.Playlist
import com.example.spotifyclone.viewmodel.PlaylistViewModel
import com.example.spotifyclone.viewmodel.PlaylistViewModelFactory
import com.example.spotifyclone.repository.PlaylistRepository
import com.example.spotifyclone.database.SpotifyDatabase
import com.example.spotifyclone.ui.theme.*

val playlistEmojis = listOf("🎵", "🎶", "🎤", "🎧", "🎸", "🎹", "🥁", "🎺", "🎷", "🎻")
val playlistColors = listOf(
    listOf(Color(0xFFB794F6), Color(0xFFD4BBFF)),
    listOf(Color(0xFFFF6B6B), Color(0xFFFF8E8E)),
    listOf(Color(0xFF4ECDC4), Color(0xFF44A08D)),
    listOf(Color(0xFF667eea), Color(0xFF764ba2)),
    listOf(Color(0xFFf093fb), Color(0xFFf5576c))
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCrudPlaylist(navController: NavController) {
    val context = LocalContext.current
    val database = SpotifyDatabase.getDatabase(context)
    val repository = PlaylistRepository(database.playlistDao())
    val viewModel: PlaylistViewModel = viewModel(factory = PlaylistViewModelFactory(repository))
    
    val playlists by viewModel.playlists.collectAsState()
    
    var showDialog by remember { mutableStateOf(false) }
    var playlistEditando by remember { mutableStateOf<Playlist?>(null) }
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
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = SpotifyTextPrimary
                        )
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
                    text = "${playlists.size} playlist${if (playlists.size != 1) "s" else ""}",
                    fontSize = 14.sp,
                    color = SpotifyTextSecondary
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { 
                    playlistEditando = null
                    nomePlaylist = ""
                    descricaoPlaylist = ""
                    showDialog = true 
                },
                containerColor = SpotifyPrimary
            ) {
                Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Adicionar Playlist",
                tint = Color.White
            )
            }
        }
    ) { paddingValues ->
        if (playlists.isEmpty()) {
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(playlists) { playlist ->
                    val emojiIndex = playlist.id % playlistEmojis.size
                    val colorIndex = playlist.id % playlistColors.size
                    
                    PlaylistCardItem(
                        playlist = playlist,
                        emoji = playlistEmojis[emojiIndex],
                        gradient = playlistColors[colorIndex],
                        onEdit = {
                            playlistEditando = playlist
                            nomePlaylist = playlist.nome
                            descricaoPlaylist = playlist.descricao
                            showDialog = true
                        },
                        onDelete = { viewModel.deletePlaylist(playlist) },
                        onClick = { navController.navigate("playlist_details/${playlist.id}") }
                    )
                }
            }
        }
    }
    
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(if (playlistEditando == null) "Nova Playlist" else "Editar Playlist", color = SpotifyTextPrimary) },
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
                            if (playlistEditando == null) {
                                viewModel.insertPlaylist(
                                    Playlist(
                                        nome = nomePlaylist,
                                        descricao = descricaoPlaylist
                                    )
                                )
                            } else {
                                viewModel.updatePlaylist(
                                    playlistEditando!!.copy(
                                        nome = nomePlaylist,
                                        descricao = descricaoPlaylist
                                    )
                                )
                            }
                            showDialog = false
                        }
                    }
                ) {
                    Text(if (playlistEditando == null) "Criar" else "Salvar", color = SpotifyPrimary)
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
fun PlaylistCardItem(
    playlist: Playlist,
    emoji: String,
    gradient: List<Color>,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
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
                .clickable { onClick() }
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
            ) {
                IconButton(
                    onClick = { showMenu = true },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Mais opções",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.background(SpotifyCardBackground)
                ) {
                    DropdownMenuItem(
                        text = { 
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Adicionar Músicas",
                                    tint = SpotifyPrimary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Adicionar Músicas", color = SpotifyTextPrimary)
                            }
                        },
                        onClick = {
                            showMenu = false
                            onClick()
                        }
                    )
                    HorizontalDivider(color = SpotifyTextSecondary.copy(alpha = 0.2f))
                    DropdownMenuItem(
                        text = { 
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Editar",
                                    tint = SpotifyPrimary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Editar", color = SpotifyTextPrimary)
                            }
                        },
                        onClick = {
                            showMenu = false
                            onEdit()
                        }
                    )
                    DropdownMenuItem(
                        text = { 
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Excluir",
                                    tint = Color.Red
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Excluir", color = Color.Red)
                            }
                        },
                        onClick = {
                            showMenu = false
                            onDelete()
                        }
                    )
                }
            }
            
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = emoji, fontSize = 40.sp)
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column {
                    Text(
                        text = playlist.nome,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    if (playlist.descricao.isNotBlank()) {
                        Text(
                            text = playlist.descricao,
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
