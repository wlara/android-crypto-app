package io.github.wlara.cryptoapp.data.repo.impl

import io.github.wlara.cryptoapp.data.network.CoinCapService
import io.github.wlara.cryptoapp.data.repo.AssetsPagingSource
import io.github.wlara.cryptoapp.data.repo.AssetsRepository
import javax.inject.Inject

class AssetsRepositoryImpl @Inject constructor(
    private val service: CoinCapService
) : AssetsRepository {

    override fun assetsPagingSource(): AssetsPagingSource =
        AssetsPagingSource(service)
}