package com.oishikenko.android.recruitment.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.oishikenko.android.recruitment.data.model.CookingRecord
import com.oishikenko.android.recruitment.data.repository.CookingPagingSource
import com.oishikenko.android.recruitment.data.repository.CookingRecordsRepository
import com.oishikenko.android.recruitment.data.repository.CookingRecordsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    //private var cookingRecordsRepository: CookingRecordsRepository
    private var cookingRecordsRepositoryImpl: CookingRecordsRepositoryImpl
) : ViewModel() {
    /*var cookingRecords: StateFlow<List<CookingRecord>> =
        cookingRecordsRepository.getCookingRecords(offet = 0, limit = 5).map {
            it.body()?.cookingRecords ?: emptyList<CookingRecord>()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList<CookingRecord>()
        )*/

    private val _pagedData = MutableStateFlow<PagingData<CookingRecord>>(PagingData.empty())
    var cookingRecords: StateFlow<PagingData<CookingRecord>> = _pagedData

    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 1),
                pagingSourceFactory = { CookingPagingSource(cookingRecordsRepositoryImpl) }
            ).flow.collectLatest { pagingData ->
                _pagedData.value = pagingData
            }
        }
    }

    /*fun loadCookingRecords(newOffset:Int, newLimit:Int ): StateFlow<List<CookingRecord>>{
        cookingRecords = cookingRecordsRepository.getCookingRecords(newOffset,newLimit).map {
            it.body()?.cookingRecords ?: emptyList<CookingRecord>()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList<CookingRecord>()
        )
        return cookingRecords
    }*/
}