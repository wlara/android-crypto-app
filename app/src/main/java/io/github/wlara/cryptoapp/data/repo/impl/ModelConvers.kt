package io.github.wlara.cryptoapp.data.repo.impl

import io.github.wlara.cryptoapp.data.model.Asset
import io.github.wlara.cryptoapp.data.network.model.GetAssetsResponse

fun GetAssetsResponse.Data.toDomainModel(): Asset = Asset(
    name = name,
    symbol = symbol,
    priceUsd = priceUsd,
    change24Hr = changePercent24Hr / 100,
    logoUrl = logoUrl
)