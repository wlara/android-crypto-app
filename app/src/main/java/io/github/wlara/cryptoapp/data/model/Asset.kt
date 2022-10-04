package io.github.wlara.cryptoapp.data.model

data class Asset(
    val name: String,
    val symbol: String,
    val priceUsd: Double,
    val change24Hr: Double,
    val logoUrl: String
)
