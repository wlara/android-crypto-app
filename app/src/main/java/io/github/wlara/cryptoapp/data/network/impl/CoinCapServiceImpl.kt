package io.github.wlara.cryptoapp.data.network.impl

import android.util.Log
import io.github.wlara.cryptoapp.BuildConfig.BASE_API_URL
import io.github.wlara.cryptoapp.data.network.CoinCapService
import io.github.wlara.cryptoapp.data.network.model.GetAssetsResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

private const val TAG = "CoinCapServiceImpl"

class CoinCapServiceImpl @Inject constructor(
    private val client: HttpClient
) : CoinCapService {

    override suspend fun getAssets(offset: Int, limit: Int): GetAssetsResponse {
        Log.d(TAG, "getAssets: offset=$offset, limit=$limit")
        return client.get("$BASE_API_URL/v2/assets") {
            parameter("offset", offset)
            parameter("limit", limit)
        }.body()
    }
}