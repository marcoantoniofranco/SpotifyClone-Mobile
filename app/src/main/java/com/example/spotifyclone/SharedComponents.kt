package com.example.spotifyclone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

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
