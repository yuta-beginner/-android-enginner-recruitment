package com.oishikenko.android.recruitment.data.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.oishikenko.android.recruitment.data.model.CookingRecord
import com.oishikenko.android.recruitment.data.model.CookingRecords
import kotlinx.coroutines.flow.single
import okhttp3.Response
import java.util.concurrent.Flow

class CookingPagingSource (
    private val repository: CookingRecordsRepositoryImpl
) : PagingSource<Int, CookingRecord>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CookingRecord> {
        val offset = params.key ?: 0
        val limit = 5
        Log.i("CookingPagingSource", "offsetは$offset")
        Log.i("CookingPagingSource", "limitは$limit")
        return try {
            val response = repository.getCookingRecords(offset = offset, limit = limit).single()
            val data = response.body()?.cookingRecords?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = if (data.isEmpty()) null else offset + limit
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CookingRecord>): Int? {
        TODO("Not yet implemented")
    }
}