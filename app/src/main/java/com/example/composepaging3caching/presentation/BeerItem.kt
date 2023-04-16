package com.example.composepaging3caching.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composepaging3caching.domain.Beer
import com.example.composepaging3caching.ui.theme.ComposePaging3CachingTheme

@Composable
fun BeerItem(beer: Beer, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = beer.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .weight(1F)
                    .height(150.dp)
            )
            Column() {
                name = "Beer",
                tagline = "This is a cool beer",
                firstBrewed = "07/2023",
                description = "This is a description for a beer",
                imageUrl = ""
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}