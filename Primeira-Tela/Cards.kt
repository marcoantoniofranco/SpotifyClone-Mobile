package com.example.spotifyclone

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable

fun CardSecundario (
    titulo: String, 
    color: Color,
    largura: Dp = 108.dp,
    altura: Dp = 191.dp
) {

    Column {

    Surface(
        modifier = Modifier.width(largura)
            .height(altura)
            .padding(8.dp),
        color = color,
        shadowElevation = 6.dp,
        tonalElevation = 6.dp,
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Green
        )
    }


}
}

@Preview(showBackground = true)
@Composable
fun PreviewCardSecundario(){
    CardSecundario("A", Color.White)
}