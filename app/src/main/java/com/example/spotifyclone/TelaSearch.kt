package com.example.spotifyclone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TelaSearch(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.Black,
        bottomBar = {
            MenuInferior(telaAtual = "buscar")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
        CabecalhoSearch()

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black,
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                TextField(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = "What do you want to play ?") },
                )

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Explore your musical type",
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CardSecundario("#music1", Color(0xFF8B4513), 108.dp, 191.dp)
                    CardSecundario("#music2", Color(0xFFFF8C00), 108.dp, 191.dp)
                    CardSecundario("#music3", Color(0xFF9C27B0), 108.dp, 191.dp)
                }

                Text(
                    modifier = Modifier,
                    text = "Browse all",
                    color = Color.White,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CardSecundario("Pop", Color(0xFF8B4513), 170.dp, 90.dp)
                        CardSecundario("Rock", Color(0xFFFF8C00), 170.dp, 90.dp)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CardSecundario("Jazz", Color(0xFF9C27B0), 170.dp, 90.dp)
                        CardSecundario("Classical", Color(0xFF3F51B5), 170.dp, 90.dp)
                    }
                }
            }
        }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaSearch() {
    TelaSearch()
}