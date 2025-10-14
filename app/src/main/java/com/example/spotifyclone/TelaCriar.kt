package com.example.spotifyclone

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.spotifyclone.ui.theme.*

@Composable
fun TelaCriar(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = SpotifyBackground,
        bottomBar = {
            MenuInferior(
                telaAtual = "criar",
                onNavigate = { destino -> navController.navigate(destino) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            CabecalhoCriar()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Criar",
                color = SpotifyTextPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            OpcoesCrear(navController, context)
        }
    }
}

@Composable
fun OpcoesCrear(navController: NavHostController, context: android.content.Context) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { 
                    navController.navigate("crud_playlist")
                },
            color = SpotifyCardBackground,
            shape = RoundedCornerShape(8.dp)
        ) {
            OpcaoCriar(
                icone = Icons.Default.Add,
                titulo = "Criar Playlist",
                subtitulo = "Crie e gerencie suas playlists",
                corIcone = SpotifyPrimary
            )
        }
        
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { 
                    Toast.makeText(context, "Em breve!", Toast.LENGTH_SHORT).show()
                },
            color = SpotifyCardBackground,
            shape = RoundedCornerShape(8.dp)
        ) {
            OpcaoCriar(
                icone = Icons.Default.Person,
                titulo = "Playlist Colaborativa",
                subtitulo = "Crie uma playlist com a galera",
                corIcone = Color.White
            )
        }
        
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { 
                    navController.navigate("crud_musica")
                },
            color = SpotifyCardBackground,
            shape = RoundedCornerShape(8.dp)
        ) {
            OpcaoCriar(
                icone = Icons.Default.PlayArrow,
                titulo = "Gerenciar Músicas",
                subtitulo = "Adicionar, editar ou remover músicas",
                corIcone = SpotifyPrimary
            )
        }
    }
}

@Composable
fun OpcaoCriar(
    icone: ImageVector,
    titulo: String,
    subtitulo: String,
    corIcone: Color = SpotifyTextPrimary
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier.size(60.dp),
            color = SpotifyBackground,
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icone,
                    contentDescription = titulo,
                    modifier = Modifier.size(30.dp),
                    tint = corIcone
                )
            }
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column {
            Text(
                text = titulo,
                color = SpotifyTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitulo,
                color = SpotifyTextSecondary,
                fontSize = 14.sp
            )
        }
    }
}
