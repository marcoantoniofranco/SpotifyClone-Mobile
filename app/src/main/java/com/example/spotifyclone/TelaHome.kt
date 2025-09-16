package com.example.spotifyclone

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun TelaHome(
    navController: NavHostController? = null,
    modifier: Modifier = Modifier
) {
    // Simulando dados do "backend"
    val musicasRecentes = remember { mutableStateOf(MusicRepository.getMusicasRecentes()) }
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Black,
        bottomBar = {
            MenuInferior(
                telaAtual = "home",
                onNavigate = { destino -> 
                    navController?.navigate(destino)
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item { CabecalhoComPerfil() }
            item { MenuSuperior(opcaoSelecionada = "All") }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { GridMusicasRecentes(musicasRecentes.value) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { NovoLancamento() }
        }
    }
}

@Composable
fun GridMusicasRecentes(musicas: List<Musica>) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        // Usando dados reais do backend
        musicas.chunked(2).forEach { par ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                par.forEach { musica ->
                    CardMusicaRecente(
                        titulo = musica.titulo, 
                        cor = Color(0xFF4A90E2), // Cor padrão, poderia vir do backend também
                        modifier = Modifier.weight(1f)
                    )
                }
                // Se só tem uma música no par, adiciona espaço
                if (par.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CardMusicaRecente(titulo: String, cor: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.height(60.dp),
        color = Color.DarkGray,
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Surface(
                modifier = Modifier.size(52.dp),
                color = cor,
                shape = RoundedCornerShape(4.dp)
            ) {}
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = titulo,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun NovoLancamento() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "New release from",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        
        Text(
            text = "Arctic Monkeys",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.DarkGray,
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Surface(
                    modifier = Modifier.size(80.dp),
                    color = Color.Gray,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("AM", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "I Wanna Be Yours",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Single • Arctic Monkeys",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))

                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(32.dp),
                        color = Color.Transparent,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Adicionar",
                            tint = Color.White,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    
                    Surface(
                        modifier = Modifier.size(48.dp),
                        color = Color.White,
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.Black,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaHome() {
    TelaHome()
}
