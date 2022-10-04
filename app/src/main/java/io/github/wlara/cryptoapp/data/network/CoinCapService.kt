package io.github.wlara.cryptoapp.data.network

import io.github.wlara.cryptoapp.data.network.model.GetAssetsResponse

interface CoinCapService {
    suspend fun getAssets(offset: Int, limit: Int): GetAssetsResponse
}