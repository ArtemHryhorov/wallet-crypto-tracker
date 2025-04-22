package com.crypto.wallet.feature.balance.data.datasource

import com.crypto.wallet.core.network.NetworkConstants
import com.crypto.wallet.feature.balance.data.dto.CurrencyRateDto
import com.crypto.wallet.feature.balance.domain.datasource.CurrencyRateRemoteDatasource
import com.crypto.wallet.feature.balance.domain.model.CurrencyRate
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class CurrencyRateRemoteDatasourceImpl @Inject constructor(
  private val httpClient: HttpClient,
) : CurrencyRateRemoteDatasource {
  override suspend fun getBitcoinRate(): Result<CurrencyRate> = try {
    val currencyRateDto: CurrencyRateDto = httpClient
      .get(NetworkConstants.API + "/assets/bitcoin") {
        parameter(key = "apiKey", value = NetworkConstants.API_KEY)
      }
      .body()

    Result.success(
      CurrencyRate(
        rate = currencyRateDto.data.priceUsd.toDouble(),
        timestamp = currencyRateDto.timestamp,
      )
    )
  } catch (error: Exception) {
    Result.failure(error)
  }
}