package com.example.composepaging3caching.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.composepaging3caching.data.local.BeerDatabase
import com.example.composepaging3caching.data.local.BeerEntity
import com.example.composepaging3caching.data.mappers.toBeerEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

private const val FIRST_PAGE = 1

@OptIn(ExperimentalPagingApi::class)
class BeerRemoteMediator(
    private val beerDatabase: BeerDatabase,
    private val beerApi: BeerApi
) : RemoteMediator<Int, BeerEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> FIRST_PAGE
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> getPage(state)
            }

            MediatorResult.Success(
                endOfPaginationReached = getBeers(
                    loadType = loadType,
                    page = page,
                    pageSize = state.config.pageSize
                ).isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private fun getPage(state: PagingState<Int, BeerEntity>): Int {
        val lastItem = state.lastItemOrNull()
        return if (lastItem == null) {
            FIRST_PAGE
        } else {
            (lastItem.id / state.config.pageSize) + FIRST_PAGE
        }
    }

    private suspend fun getBeers(loadType: LoadType, page: Int, pageSize: Int): List<BeerDto> {
        delay(5000)
        val beers = beerApi.getBeers(page = page, perPage = pageSize)

        beerDatabase.withTransaction {
            if (loadType == LoadType.REFRESH) {
                beerDatabase.beerDao.clearAll()
            }
            val beerEntities = beers.map { it.toBeerEntity() }
            beerDatabase.beerDao.upsertAll(beerEntities)
        }

        return beers
    }
}