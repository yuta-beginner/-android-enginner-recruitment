package com.oishikenko.android.recruitment.data.repository

import com.oishikenko.android.recruitment.data.model.CookingRecords
import com.oishikenko.android.recruitment.data.network.CookingRecordsNetworkApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class CookingRecordsRepositoryImpl @Inject constructor (
    var cookingRecordsNetworkApi: CookingRecordsNetworkApi
): CookingRecordsRepository {
    /**
     * issues 1-4はlimitを5から30に書き換えることで実装できました。
     */
    override fun getCookingRecords(offset: Int, limit: Int): Flow<Response<CookingRecords>> = flow {
        emit(cookingRecordsNetworkApi.getCookingRecords(offset = offset, limit = limit))
    }
}