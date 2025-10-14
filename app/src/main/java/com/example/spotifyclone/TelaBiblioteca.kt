package com.example.spotifyclone

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spotifyclone.ui.theme.SpotifyCloneTheme
import com.example.spotifyclone.ui.theme.*
import androidx.navigation.NavHostController
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.Icons
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box

@Composable
fun TelaBiblioteca(
    navController: NavHostController? = null,
) {
    Scaffold(
        modifier = Modifier,
        containerColor = SpotifyBackground,
        bottomBar = {
            MenuInferior(
                telaAtual = "biblioteca",
                onNavigate = { destino ->
                    navController?.navigate(destino)
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item { CabecalhoBiblioteca() }
            item { Opcoes(navController) }
            item { Recentes() }
            item { ListaRecentes() }
        }
    }
}


@Composable
fun Opcoes(navController: NavHostController? = null){
    val context = LocalContext.current
    
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ){
        Column {
            Button(
                onClick = { 
                    try {
                        Toast.makeText(context, "Filtro Playlists selecionado", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        // Evita crashes por problemas de contexto
                    }
                }
            ){
                Text("Playlists")
            }
        }
        Spacer(modifier = Modifier.width(5.dp))
        Column {
            Button(
                onClick = { 
                    try {
                        Toast.makeText(context, "Filtro Albuns selecionado", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        // Evita crashes por problemas de contexto
                    }
                }
            ){
                Text("Albuns")
            }
        }
        Spacer(modifier = Modifier.width(5.dp))
        Column {
            Button(
                onClick = { 
                    try {
                        Toast.makeText(context, "Filtro Artistas selecionado", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        // Evita crashes por problemas de contexto
                    }
                }
            ){
                Text("Artistas")
            }
        }
        Spacer(modifier = Modifier.width(5.dp))
        Column {
            Button(
                onClick = { 
                    navController?.navigate("crud_playlist") {
                        launchSingleTop = true
                    }
                }
            ){
                Text("Gerenciar")
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
                tint = Color(0xFF9127F5)
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
                tint = Color(0xFF9127F5)
            )}

    }
}



@Composable
fun Playlist(titulo: String, subtitulo: String, @DrawableRes imagemId: Int? = null, // Opcional para imagem
             icone: ImageVector? = null, ehRedondo: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 10.dp)
    ) {
        Surface(
            modifier = Modifier.size(70.dp),
            color = SpotifyCardBackground,
            shape = if (ehRedondo) CircleShape else RoundedCornerShape(4.dp)
        ) {
            when {
                imagemId != null -> {
                    Image(
                        painter = painterResource(id = imagemId),
                        contentDescription = titulo,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                icone != null -> {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icone,
                            contentDescription = titulo,
                            modifier = Modifier.size(40.dp),
                            tint = Color.Magenta
                        )
                    }
                }
                else -> {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = titulo,
                            modifier = Modifier.size(40.dp),
                            tint = Color(0xFF9127F5)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(15.dp))
        Column {
            Text(
                text = titulo,
                style = MaterialTheme.typography.headlineSmall,
                color = SpotifyTextPrimary
            )
            Text(
                text = subtitulo,
                style = MaterialTheme.typography.titleSmall,
                color = SpotifyTextPrimary
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
            imagemId = R.drawable.expresso,
            ehRedondo = false
        )
        Playlist(
            titulo = "Yuuri",
            subtitulo = "Artista -",
            imagemId = R.drawable.yuuri,
            ehRedondo = true
        )
        Playlist(
            titulo = "Pop",
            subtitulo = "Playlist -",
            imagemId = R.drawable.pop,
            ehRedondo = false
        )
        Playlist(
            titulo = "Rock",
            subtitulo = "Playlist -",
            imagemId = R.drawable.rock,
            ehRedondo = false
        )
        Playlist(
            titulo = "Bruno Mars",
            subtitulo = "Artista -",
            imagemId = R.drawable.brunomars,
            ehRedondo = true
        )
        Playlist(
            titulo = "Enhypen",
            subtitulo = "Artista -",
            imagemId = R.drawable.enhypen,
            ehRedondo = true
        )
        Playlist(
            titulo = "Over Each Other",
            subtitulo = "Single - Linkin Park",
            imagemId = R.drawable.overeachother,
            ehRedondo = false
        )


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
        MenuInferior(telaAtual = "biblioteca")
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item { CabecalhoBiblioteca() }
            item { Opcoes() }
            item { Recentes() }
            item { ListaRecentes() }
        }
    }
}
