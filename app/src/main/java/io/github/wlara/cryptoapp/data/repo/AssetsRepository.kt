package io.github.wlara.cryptoapp.data.repo

interface AssetsRepository {
    fun assetsPagingSource(): AssetsPagingSource
}