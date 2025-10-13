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
import com.example.spotifyclone.ui.theme.*

@Composable
fun BotaoMenuPersonalizavel(
    texto: String, 
    icone: ImageVector, 
    selecionado: Boolean, 
    corSelecionado: Color = SpotifyTextPrimary,
    corNaoSelecionado: Color = SpotifyUnselected,
    tamanhoIcone: androidx.compose.ui.unit.Dp = 24.dp,
    tamanhoTexto: androidx.compose.ui.unit.TextUnit = 12.sp,
    onClick: () -> Unit = {}
) {
    TextButton(onClick = onClick) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val cor = if (selecionado) corSelecionado else corNaoSelecionado

            Icon(
                imageVector = icone,
                contentDescription = texto,
                tint = cor,
                modifier = Modifier.size(tamanhoIcone)
            )
            Text(
                text = texto,
                color = cor,
                fontSize = tamanhoTexto
            )
        }
    }
}

// Função de conveniência para manter compatibilidade
@Composable
fun BotaoMenu(texto: String, icone: ImageVector, selecionado: Boolean, onClick: () -> Unit = {}) {
    BotaoMenuPersonalizavel(
        texto = texto,
        icone = icone,
        selecionado = selecionado,
        onClick = onClick
    )
}

@Composable
fun MenuInferior(
    telaAtual: String = "biblioteca",
    onNavigate: (String) -> Unit = {}
) {
    Surface(color = SpotifyBackground) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BotaoMenuPersonalizavel(
                texto = "Início",
                icone = Icons.Default.Home,
                selecionado = telaAtual == "home",
                corSelecionado = SpotifySelected,
                onClick = { onNavigate("home") }
            )
            BotaoMenuPersonalizavel(
                texto = "Buscar",
                icone = Icons.Default.Search,
                selecionado = telaAtual == "search",
                corSelecionado = SpotifySelected,
                onClick = { onNavigate("search") }
            )
            BotaoMenuPersonalizavel(
                texto = "Sua Biblioteca",
                icone = Icons.Default.Menu,
                selecionado = telaAtual == "biblioteca",
                corSelecionado = SpotifySelected,
                onClick = { onNavigate("biblioteca") }
            )
            BotaoMenuPersonalizavel(
                texto = "Criar",
                icone = Icons.Default.Add,
                selecionado = telaAtual == "criar",
                corSelecionado = SpotifySelected,
                onClick = { onNavigate("criar") }
            )
        }
    }
}

@Composable
fun CabecalhoPersonalizavel(
    titulo: String? = null,
    corPerfil: Color = SpotifyUnselected,
    corTintePerfil: Color = SpotifyTextPrimary,
    tamanhoPerfil: androidx.compose.ui.unit.Dp = 40.dp,
    mostrarIconesDireita: Boolean = false,
    iconesDireita: List<Pair<ImageVector, Color>> = emptyList(),
    tamanhoTitulo: androidx.compose.ui.unit.TextUnit = 25.sp,
    corTitulo: Color = SpotifyTextPrimary
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (titulo != null || mostrarIconesDireita) Arrangement.SpaceBetween else Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Foto do perfil
            Surface(
                modifier = Modifier.size(tamanhoPerfil),
                shape = CircleShape,
                color = corPerfil
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = corTintePerfil,
                    modifier = Modifier.padding(8.dp)
                )
            }
            
            if (titulo != null) {
                androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = titulo,
                    fontSize = tamanhoTitulo,
                    fontWeight = FontWeight.Bold,
                    color = corTitulo
                )
            }
        }
        
        if (mostrarIconesDireita && iconesDireita.isNotEmpty()) {
            Row {
                iconesDireita.forEachIndexed { index, (icone, cor) ->
                    if (index > 0) {
                        androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(16.dp))
                    }
                    Icon(
                        imageVector = icone,
                        contentDescription = "",
                        modifier = Modifier.size(30.dp),
                        tint = cor
                    )
                }
            }
        } else if (titulo == "Search") {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notificações",
                tint = SpotifyTextPrimary,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

// Funções de conveniência para manter compatibilidade - MELHORADAS
@Composable
fun CabecalhoComPerfil() {
    CabecalhoPersonalizavel(
        corPerfil = SpotifyPrimary,
        tamanhoPerfil = 42.dp
    )
}

@Composable
fun CabecalhoBiblioteca() {
    CabecalhoPersonalizavel(
        titulo = "Sua Biblioteca",
        corPerfil = SpotifyPrimary,
        corTintePerfil = SpotifyTextOnPrimary,
        tamanhoPerfil = 42.dp,
        tamanhoTitulo = 28.sp,
        mostrarIconesDireita = true,
        iconesDireita = listOf(
            Icons.Default.Search to SpotifyTextPrimary,
            Icons.Default.Add to SpotifyTextPrimary
        )
    )
}

@Composable
fun CabecalhoSearch() {
    CabecalhoPersonalizavel(
        titulo = "Search",
        corPerfil = SpotifyPrimary,
        tamanhoPerfil = 42.dp,
        tamanhoTitulo = 26.sp,
        mostrarIconesDireita = true,
        iconesDireita = listOf(
            Icons.Default.Notifications to SpotifyTextPrimary
        )
    )
}

@Composable
fun CabecalhoCriar() {
    CabecalhoPersonalizavel(
        titulo = "Criar",
        corPerfil = SpotifyPrimary,
        tamanhoPerfil = 42.dp,
        tamanhoTitulo = 26.sp
    )
}

@Composable
fun MenuSuperior(opcaoSelecionada: String = "All") {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BotaoMenuSuperiorPersonalizavel("All", opcaoSelecionada == "All")
        BotaoMenuSuperiorPersonalizavel("Music", opcaoSelecionada == "Music")
        BotaoMenuSuperiorPersonalizavel("Podcasts", opcaoSelecionada == "Podcasts")
    }
}

@Composable
fun BotaoMenuSuperiorPersonalizavel(
    texto: String, 
    selecionado: Boolean,
    corSelecionado: Color = SpotifySelected,
    corNaoSelecionado: Color = SpotifyCardBackground,
    corTextoSelecionado: Color = SpotifyTextOnPrimary,
    corTextoNaoSelecionado: Color = SpotifyTextPrimary,
    raioArredondamento: androidx.compose.ui.unit.Dp = 20.dp,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selecionado) corSelecionado else corNaoSelecionado
        ),
        shape = RoundedCornerShape(raioArredondamento)
    ) {
        Text(
            text = texto,
            color = if (selecionado) corTextoSelecionado else corTextoNaoSelecionado
        )
    }
}

// Função de conveniência para manter compatibilidade
@Composable
fun BotaoMenuSuperior(texto: String, selecionado: Boolean) {
    BotaoMenuSuperiorPersonalizavel(
        texto = texto,
        selecionado = selecionado
    )
}
