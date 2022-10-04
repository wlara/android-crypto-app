package io.github.wlara.cryptoapp.data.network

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.wlara.cryptoapp.data.network.impl.CoinCapServiceImpl
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideHttpClient(): HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }
            )
        }
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.HEADERS
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModuleBinds {
    @Binds
    abstract fun bindCoinCapService(service: CoinCapServiceImpl): CoinCapService
}
