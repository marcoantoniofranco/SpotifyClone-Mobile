package com.example.spotifyclone

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun TelaCriar(
    navController: NavHostController? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Black,
        bottomBar = {
            MenuInferior(
                telaAtual = "criar",
                onNavigate = { destino -> 
                    navController?.navigate(destino)
                }
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
                text = "Suas músicas estão com saudade",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            OpcoesCrear()
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Botão para demonstrar Toast
            Button(
                onClick = {
                    Toast.makeText(context, "Playlist criada com sucesso!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Criar Nova Playlist")
            }
        }
    }
}

@Composable
fun OpcoesCrear() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OpcaoCriar(
            icone = Icons.Default.Add,
            titulo = "Playlist",
            subtitulo = "Crie uma playlist com músicas ou episódios",
            corIcone = Color.White
        )
        
        OpcaoCriar(
            icone = Icons.Default.Person,
            titulo = "Playlist colaborativa",
            subtitulo = "Crie uma playlist com a galera",
            corIcone = Color.White
        )
        
        OpcaoCriar(
            icone = Icons.Default.PlayArrow,
            titulo = "Match",
            subtitulo = "Junte os gostos musicais da sua galera em uma playlist",
            corIcone = Color.White
        )
    }
}

@Composable
fun OpcaoCriar(
    icone: ImageVector,
    titulo: String,
    subtitulo: String,
    corIcone: Color = Color.White
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Surface(
            modifier = Modifier.size(60.dp),
            color = Color.DarkGray,
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
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitulo,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaCriar() {
    TelaCriar()
}