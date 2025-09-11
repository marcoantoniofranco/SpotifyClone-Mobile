package com.example.spotifyclone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BotaoMenu(texto: String, icone: ImageVector, selecionado: Boolean, onClick: () -> Unit = {}){
    TextButton(onClick = onClick){
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            val cor = if (selecionado) Color.White else Color.Gray

            Icon(
                imageVector = icone,
                contentDescription = texto,
                tint = cor
            )
            Text(
                text = texto,
                color = cor
            )
        }
    }
}

@Composable
fun MenuInferior(telaAtual: String = "biblioteca") {
    Surface(color = Color.Black) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BotaoMenu(
                texto = "Início",
                icone = Icons.Default.Home,
                selecionado = telaAtual == "inicio"
            )
            BotaoMenu(
                texto = "Buscar",
                icone = Icons.Default.Search,
                selecionado = telaAtual == "buscar"
            )
            BotaoMenu(
                texto = "Sua Biblioteca",
                icone = Icons.Default.Menu,
                selecionado = telaAtual == "biblioteca"
            )
            BotaoMenu(
                texto = "Criar",
                icone = Icons.Default.Add,
                selecionado = telaAtual == "criar"
            )
        }
    }
}

@Composable
fun CabecalhoCriar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            color = Color.Green
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Perfil",
                tint = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun CabecalhoBiblioteca() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Foto do perfil verde
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = Color.Green
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = Color.Black,
                    modifier = Modifier.padding(8.dp)
                )
            }
            
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = "Sua Biblioteca",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        
        Row {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                modifier = Modifier.size(30.dp),
                tint = Color.DarkGray
            )
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Adicionar",
                modifier = Modifier.size(30.dp),
                tint = Color.DarkGray
            )
        }
    }
}

@Composable
fun CabecalhoSearch() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Foto do perfil
        Surface(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            color = Color.Gray
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Perfil",
                tint = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
        
        Text(
            text = "Search",
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notificações",
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun MenuSuperior(opcaoSelecionada: String = "All") {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BotaoMenuSuperior("All", opcaoSelecionada == "All")
        BotaoMenuSuperior("Music", opcaoSelecionada == "Music")
        BotaoMenuSuperior("Podcasts", opcaoSelecionada == "Podcasts")
    }
}

@Composable
fun BotaoMenuSuperior(texto: String, selecionado: Boolean) {
    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selecionado) Color.Green else Color.DarkGray
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = texto,
            color = if (selecionado) Color.Black else Color.White
        )
    }
}
