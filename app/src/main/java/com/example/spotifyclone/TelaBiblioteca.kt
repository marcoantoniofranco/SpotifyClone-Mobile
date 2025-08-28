package com.example.spotifyclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spotifyclone.ui.theme.SpotifyCloneTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpotifyCloneTheme {
                MenuFinalContent()
            }
        }
    }
}

@Composable
fun Biblioteca(){
    Surface(
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
    ){
        LazyColumn{
            item{ CabecalhoBiblioteca() }
            item { Opcoes() }
            item{ Recentes() }
            item{ ListaRecentes() }
        }
    }

}

@Composable
fun CabecalhoBiblioteca(){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ){
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "",
            modifier = Modifier.size(40.dp),
            tint = Color.Green
        )

        Spacer(modifier = Modifier.width(5.dp))

        Column(horizontalAlignment = Alignment.Start){
            Text("Sua Biblioteca", style = MaterialTheme.typography.headlineLarge, color = Color.White)
        }
        Spacer(modifier = Modifier.width(85.dp))

        Column(horizontalAlignment = Alignment.End){
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp),
                tint = Color.DarkGray
            )
        }
        Column(horizontalAlignment = Alignment.End){
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "",
                modifier = Modifier
                    .size(30.dp),
                tint = Color.DarkGray
            )
        }
    }

}

@Composable
fun Opcoes(){
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ){
        Column {
            Button(onClick = { }){
                Text("Playlists")
            }
        }
        Spacer(modifier = Modifier.width(5.dp))
        Column {
            Button(onClick = { }){
                Text("Albuns")
            }
        }
        Spacer(modifier = Modifier.width(5.dp))
        Column {
            Button(onClick = { }){
                Text("Artistas")
            }
        }
    }
}

@Composable
fun Recentes() {
    Row(
        verticalAlignment = Alignment.CenterVertically,

        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        Column(horizontalAlignment = Alignment.Start){
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "",
                modifier = Modifier.size(40.dp),
                tint = Color.Green
            )}
        Spacer(modifier = Modifier.width(5.dp))

        Column(horizontalAlignment = Alignment.Start){
            Text("Recentes", style = MaterialTheme.typography.headlineSmall, color = Color.White)
        }
        Spacer(modifier = Modifier.width(200.dp))
        Column(horizontalAlignment = Alignment.End){
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "",
                modifier = Modifier.size(40.dp),
                tint = Color.Green
            )}

    }
}



@Composable
fun Playlist(titulo: String, subtitulo: String, icone: ImageVector, ehRedondo: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 10.dp)
    ) {
        Surface(
            modifier = Modifier.size(70.dp),
            color = Color.DarkGray,
            shape = if (ehRedondo) CircleShape else RoundedCornerShape(4.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icone,
                    contentDescription = titulo,
                    modifier = Modifier.size(40.dp),
                    tint = Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.width(15.dp))
        Column {
            Text(
                text = titulo,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Text(
                text = subtitulo,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
        }
    }
}

@Composable
fun ListaRecentes() {
    Column {
        Playlist(
            titulo = "Músicas Curtidas",
            subtitulo = "Playlist -",
            icone = Icons.Default.Favorite,
            ehRedondo = false
        )
        Playlist(
            titulo = "Espresso",
            subtitulo = "Single - Sabrina Carpenter",
            icone = Icons.Default.Star,
            ehRedondo = false
        )
        Playlist(
            titulo = "Yuuri",
            subtitulo = "Artista -",
            icone = Icons.Default.Person,
            ehRedondo = true
        )
        Playlist(
            titulo = "Pop",
            subtitulo = "Playlist -",
            icone = Icons.Default.Star,
            ehRedondo = false
        )
        Playlist(
            titulo = "Rock",
            subtitulo = "Playlist -",
            icone = Icons.Default.Star,
            ehRedondo = false
        )
        Playlist(
            titulo = "Bruno Mars",
            subtitulo = "Artista -",
            icone = Icons.Default.Person,
            ehRedondo = true
        )
        Playlist(
            titulo = "Enhypen",
            subtitulo = "Artista -",
            icone = Icons.Default.Person,
            ehRedondo = true
        )
        Playlist(
            titulo = "Over Each Other",
            subtitulo = "Single - Linkin Park",
            icone = Icons.Default.Star,
            ehRedondo = false
        )


    }
}


@Composable
fun BotaoMenu(texto: String, icone: ImageVector, selecionado: Boolean){
    TextButton(onClick = { }){
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

@Preview(showBackground = true)
@Composable
fun PreviewMenuFinal() {
    SpotifyCloneTheme {
        MenuFinalContent()
    }
}

@Composable
fun MenuFinalContent() {
    Scaffold(containerColor = Color.Black, bottomBar = {
        Surface(color = Color.Black) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BotaoMenu(
                    texto = "Início",
                    icone = Icons.Default.Home,
                    selecionado = false
                )
                BotaoMenu(
                    texto = "Buscar",
                    icone = Icons.Default.Search,
                    selecionado = false
                )
                BotaoMenu(
                    texto = "Sua Biblioteca",
                    icone = Icons.Default.Menu,
                    selecionado = true
                )
                BotaoMenu(
                    texto = "Criar",
                    icone = Icons.Default.Add,
                    selecionado = false
                )
            }
        }
    }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Biblioteca()
        }
    }
}


