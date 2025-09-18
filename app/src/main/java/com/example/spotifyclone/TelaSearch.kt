package com.example.spotifyclone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun TelaSearch(
    navController: NavHostController? = null,
    modifier: Modifier = Modifier
) {
    val searchText = remember { mutableStateOf("") }
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Black,
        bottomBar = {
            MenuInferior(
                telaAtual = "search",
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
            item { CabecalhoSearch() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CampoSearch(searchText.value) { searchText.value = it } }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { SecaoExploreType() }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { SecaoBrowseAll() }
        }
    }
}

@Composable
fun CampoSearch(
    searchText: String,
    onSearchChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { 
                Text(
                    text = "What do you want to play?",
                    color = Color.Gray
                ) 
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(4.dp)
        )
    }
}

@Composable
fun SecaoExploreType() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Explore your musical type",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CardSecundario(
                titulo = "#permanent wave",
                largura = 108.dp,
                altura = 160.dp,
                imagemResource = R.drawable.thumb_verticale
            )
            CardSecundario(
                titulo = "#madebetter",
                largura = 108.dp,
                altura = 160.dp,
                imagemResource = R.drawable.thumb_verticale_1
            )
            CardSecundario(
                titulo = "#dance rock",
                largura = 108.dp,
                altura = 160.dp,
                imagemResource = R.drawable.thumb_verticale_2
            )
        }
    }
}

@Composable
fun SecaoBrowseAll() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Browse all",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CardSecundario(
                    titulo = "Music",
                    imagemResource = R.drawable.browse,
                    largura = 170.dp,
                    altura = 90.dp
                )
                CardSecundario(
                    titulo = "Podcast",
                    imagemResource = R.drawable.browse1,
                    largura = 170.dp,
                    altura = 90.dp
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CardSecundario(
                    titulo = "Live Events",
                    imagemResource = R.drawable.browse3,
                    largura = 170.dp,
                    altura = 90.dp
                )
                CardSecundario(
                    titulo = "Made For You",
                    imagemResource = R.drawable.browse4,
                    largura = 170.dp,
                    altura = 90.dp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaSearch() {
    TelaSearch()
}