package io.github.wlara.cryptoapp.data.network.model


import io.github.wlara.cryptoapp.BuildConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAssetsResponse(
    @SerialName("data")
    val `data`: List<Data>,
    @SerialName("timestamp")
    val timestamp: Long
) {
    @Serializable
    data class Data(
        @SerialName("changePercent24Hr")
        val changePercent24Hr: Double,
        @SerialName("explorer")
        val explorer: String?,
        @SerialName("id")
        val id: String,
        @SerialName("marketCapUsd")
        val marketCapUsd: Double,
        @SerialName("maxSupply")
        val maxSupply: Double?,
        @SerialName("name")
        val name: String,
        @SerialName("priceUsd")
        val priceUsd: Double,
        @SerialName("rank")
        val rank: Int,
        @SerialName("supply")
        val supply: Double,
        @SerialName("symbol")
        val symbol: String,
        @SerialName("volumeUsd24Hr")
        val volumeUsd24Hr: Double?,
        @SerialName("vwap24Hr")
        val vwap24Hr: Double?
    ) {
        val logoUrl: String
            get() = "${BuildConfig.BASE_IMAGE_URL}/${symbol.lowercase()}@2x.png"
    }
}