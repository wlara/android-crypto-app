package io.github.wlara.cryptoapp.data.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.wlara.cryptoapp.data.model.Asset
import io.github.wlara.cryptoapp.data.network.CoinCapService
import io.github.wlara.cryptoapp.data.repo.impl.toDomainModel

class AssetsPagingSource(
    private val service: CoinCapService
) : PagingSource<Int, Asset>() {

    override fun getRefreshKey(state: PagingState<Int, Asset>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Asset> {
        return try {
            val offset = params.key ?: 0
            val assets = service.getAssets(
                offset = offset,
                limit = params.loadSize
            ).data.map { it.toDomainModel() }
            LoadResult.Page(
                data = assets,
                prevKey = if (offset >= params.loadSize) offset - params.loadSize else null,
                nextKey = if (assets.isEmpty()) null else offset + params.loadSize
            )
        } catch (error: Throwable) {
            LoadResult.Error(error)
        }
    }
}
