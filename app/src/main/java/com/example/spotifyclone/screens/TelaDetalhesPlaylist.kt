package com.example.spotifyclone.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.produceState
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
import com.example.spotifyclone.models.Musica
import com.example.spotifyclone.models.Playlist
import com.example.spotifyclone.ui.theme.*
import com.example.spotifyclone.viewmodel.PlaylistMusicaViewModel

val playlistEmojisDetalhes = listOf("🎵", "🎶", "🎤", "🎧", "🎸", "🎹", "🥁", "🎺", "🎷", "🎻")
val playlistGradientsDetalhes = listOf(
    listOf(Color(0xFFB794F6), Color(0xFFD4BBFF)),
    listOf(Color(0xFFFF6B6B), Color(0xFFFF8E8E)),
    listOf(Color(0xFF4ECDC4), Color(0xFF44A08D)),
    listOf(Color(0xFF667eea), Color(0xFF764ba2)),
    listOf(Color(0xFFf093fb), Color(0xFFf5576c))
)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaDetalhesPlaylist(
    navController: NavController,
    playlistId: Long
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val database = com.example.spotifyclone.database.SpotifyDatabase.getDatabase(context)
    val playlistRepository = com.example.spotifyclone.repository.PlaylistRepository(database.playlistDao())
    val musicaRepository = com.example.spotifyclone.repository.MusicaRepository(database.musicaDao())
    val playlistMusicaRepository = com.example.spotifyclone.repository.PlaylistMusicaRepository(database.playlistMusicaDao())
    
    val viewModel: PlaylistMusicaViewModel = viewModel(
        factory = com.example.spotifyclone.viewmodel.PlaylistMusicaViewModelFactory(
            playlistRepository,
            musicaRepository,
            playlistMusicaRepository
        )
    )
    
    val playlist by produceState<Playlist?>(initialValue = null, playlistId) {
        value = viewModel.getPlaylistById(playlistId)
    }
    val musicasNaPlaylist by viewModel.getMusicasFromPlaylist(playlistId.toInt()).collectAsState(initial = emptyList())
    val todasMusicas by viewModel.allMusicas.collectAsState()
    
    var showAddMusicBottomSheet by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var musicaParaRemover by remember { mutableStateOf<Musica?>(null) }
    
    val bottomSheetState = rememberModalBottomSheetState()
    
    // Músicas disponíveis para adicionar (que não estão na playlist)
    val musicasDisponiveis = todasMusicas.filter { musica ->
        musicasNaPlaylist.none { it.id == musica.id }
    }
    
    playlist?.let { playlistAtual ->
        val emojiIndex = playlistAtual.id % playlistEmojisDetalhes.size
        val gradientIndex = playlistAtual.id % playlistGradientsDetalhes.size
        val emoji = playlistEmojisDetalhes[emojiIndex]
        val gradient = playlistGradientsDetalhes[gradientIndex]
        
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Detalhes da Playlist") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = SpotifyBackground,
                        titleContentColor = SpotifyTextPrimary,
                        navigationIconContentColor = SpotifyTextPrimary
                    )
                )
            },
            floatingActionButton = {
                if (musicasDisponiveis.isNotEmpty()) {
                    FloatingActionButton(
                        onClick = { showAddMusicBottomSheet = true },
                        containerColor = SpotifyPrimary,
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Adicionar Música")
                    }
                }
            },
            containerColor = SpotifyBackground
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header da playlist
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
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
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = emoji.toString(),
                                    fontSize = 64.sp,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )
                                
                                Text(
                                    text = playlistAtual.nome,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                
                                if (playlistAtual.descricao.isNotBlank()) {
                                    Text(
                                        text = playlistAtual.descricao,
                                        fontSize = 14.sp,
                                        color = Color.White.copy(alpha = 0.8f),
                                        textAlign = TextAlign.Center,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                                
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(top = 12.dp)
                                ) {
                                    Icon(
                                        Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = Color.White.copy(alpha = 0.7f),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (musicasNaPlaylist.isEmpty()) "Nenhuma música" 
                                              else "${musicasNaPlaylist.size} música${if (musicasNaPlaylist.size > 1) "s" else ""}",
                                        fontSize = 13.sp,
                                        color = Color.White.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }
                    }
                }
                
                // Lista de músicas
                if (musicasNaPlaylist.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "🎵",
                                fontSize = 48.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            Text(
                                text = "Playlist vazia",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = SpotifyTextPrimary,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = if (musicasDisponiveis.isNotEmpty()) 
                                      "Adicione algumas músicas tocando no botão +" 
                                      else "Crie algumas músicas primeiro na aba Criar",
                                fontSize = 14.sp,
                                color = SpotifyTextSecondary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                } else {
                    item {
                        Text(
                            text = "Músicas",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = SpotifyTextPrimary,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                    
                    items(musicasNaPlaylist) { musica ->
                        MusicaItemPlaylist(
                            musica = musica,
                            onRemove = {
                                musicaParaRemover = musica
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
        }
        
        // BottomSheet para adicionar músicas
        if (showAddMusicBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showAddMusicBottomSheet = false },
                sheetState = bottomSheetState,
                containerColor = SpotifyCardBackground
            ) {
                AddMusicBottomSheetContent(
                    musicasDisponiveis = musicasDisponiveis,
                    onAddMusic = { musica ->
                        viewModel.addMusicaToPlaylist(playlistId.toInt(), musica.id)
                        showAddMusicBottomSheet = false
                    },
                    onCancel = { showAddMusicBottomSheet = false }
                )
            }
        }
        
        // Dialog de confirmação para remover música
        if (showDeleteDialog && musicaParaRemover != null) {
            AlertDialog(
                onDismissRequest = { 
                    showDeleteDialog = false
                    musicaParaRemover = null
                },
                title = { Text("Remover música") },
                text = { Text("Deseja remover \"${musicaParaRemover!!.titulo}\" desta playlist?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.removeMusicaFromPlaylist(playlistId.toInt(), musicaParaRemover!!.id)
                            showDeleteDialog = false
                            musicaParaRemover = null
                        }
                    ) {
                        Text("Remover", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                            musicaParaRemover = null
                        }
                    ) {
                        Text("Cancelar")
                    }
                },
                containerColor = SpotifyCardBackground,
                titleContentColor = SpotifyTextPrimary,
                textContentColor = SpotifyTextSecondary
            )
        }
    }
}

@Composable
fun MusicaItemPlaylist(
    musica: Musica,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = SpotifyCardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone de música
            Surface(
                modifier = Modifier.size(48.dp),
                color = SpotifyPrimary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = SpotifyPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Informações da música
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = musica.titulo,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = SpotifyTextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = musica.artista,
                    fontSize = 14.sp,
                    color = SpotifyTextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            // Botão de remover
            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                            Icons.Default.Delete,
                            contentDescription = "Remover da playlist",
                            tint = Color.Red.copy(alpha = 0.7f),
                            modifier = Modifier.size(20.dp)
                        )
            }
        }
    }
}

@Composable
fun AddMusicBottomSheetContent(
    musicasDisponiveis: List<Musica>,
    onAddMusic: (Musica) -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = "Adicionar Músicas",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = SpotifyTextPrimary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        if (musicasDisponiveis.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎵",
                    fontSize = 48.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Nenhuma música disponível",
                    fontSize = 16.sp,
                    color = SpotifyTextPrimary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Crie algumas músicas primeiro na aba Criar",
                    fontSize = 14.sp,
                    color = SpotifyTextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        } else {
            Text(
                text = "${musicasDisponiveis.size} música${if (musicasDisponiveis.size > 1) "s" else ""} disponível${if (musicasDisponiveis.size > 1) "eis" else ""}",
                fontSize = 14.sp,
                color = SpotifyTextSecondary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.heightIn(max = 400.dp)
            ) {
                items(musicasDisponiveis) { musica ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onAddMusic(musica) },
                        colors = CardDefaults.cardColors(containerColor = SpotifyBackground),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                modifier = Modifier.size(40.dp),
                                color = SpotifyPrimary.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = SpotifyPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                                }
                            }
                            
                            Spacer(modifier = Modifier.width(12.dp))
                            
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = musica.titulo,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = SpotifyTextPrimary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = musica.artista,
                                    fontSize = 12.sp,
                                    color = SpotifyTextSecondary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Adicionar",
                                tint = SpotifyPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = SpotifyTextSecondary
            )
        ) {
            Text("Fechar")
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}