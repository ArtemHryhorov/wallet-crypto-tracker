package com.crypto.wallet.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
  @Provides
  @Singleton
  fun provideHttpClient(): HttpClient {
    return HttpClient(Android) {
      install(ContentNegotiation) {
        json(
          Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
          }
        )
      }
      install(DefaultRequest) {
        headers.append("Content-Type", "application/json")
      }
    }
  }
}
