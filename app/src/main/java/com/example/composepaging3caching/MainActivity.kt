package com.example.composepaging3caching

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.composepaging3caching.presentation.BeerScreen
import com.example.composepaging3caching.presentation.BeerViewModel
import com.example.composepaging3caching.ui.theme.ComposePaging3CachingTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePaging3CachingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    val viewModel = hiltViewModel<BeerViewModel>()
                    val beers = viewModel.beers
                    BeerScreen(beers = beers)
                }
            }
        }
    }
}