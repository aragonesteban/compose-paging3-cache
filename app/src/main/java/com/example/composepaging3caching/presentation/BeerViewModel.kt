package com.example.composepaging3caching.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.composepaging3caching.data.local.BeerEntity
import com.example.composepaging3caching.data.mappers.toBeer
import com.example.composepaging3caching.domain.Beer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(
    pager: Pager<Int, BeerEntity>
) : ViewModel() {

    private val _beers = mutableStateListOf<Beer>()

    init {
        viewModelScope.launch {
            pager.flow.collectLatest { pagingData ->
                _beers.clear()
                pagingData.map {
                    _beers.add(it.toBeer())
                }
            }
        }
    }

    val beers: List<Beer> get() = _beers

    val beerPagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it.toBeer() }
        }
        .cachedIn(viewModelScope)

}