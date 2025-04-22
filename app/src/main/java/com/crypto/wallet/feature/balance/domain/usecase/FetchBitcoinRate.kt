package com.crypto.wallet.feature.balance.domain.usecase

import android.util.Log
import com.crypto.wallet.feature.balance.domain.datasource.CurrencyRateLocalDatasource
import com.crypto.wallet.feature.balance.domain.datasource.CurrencyRateRemoteDatasource
import kotlinx.coroutines.flow.first

import javax.inject.Inject

private const val HOUR_IN_MILLIS = 1 * 60 * 60 * 1000

class FetchBitcoinRateUseCase @Inject constructor(
  private val localDatasource: CurrencyRateLocalDatasource,
  private val remoteDatasource: CurrencyRateRemoteDatasource,
) : FetchBitcoinRate {
  override suspend fun invoke() {
    localDatasource
      .getBitcoinRate()
      .first()
      .fold(
        onSuccess = { currencyRate ->
          if (isLastFetchOlderThanHour(currencyRate.timestamp)) fetchRemoteRate()
        },
        onFailure = { error ->
          if (error is NoSuchElementException) fetchRemoteRate()
        }
      )
  }

  private suspend fun fetchRemoteRate() {
    remoteDatasource
      .getBitcoinRate()
      .onSuccess { newRate ->
        Log.d("FetchBitcoinRateUseCase", "Rate updated: $newRate")
        localDatasource.updateBitcoinRate(newRate)
      }
  }

  private fun isLastFetchOlderThanHour(
    lastFetchTimestamp: Long
  ): Boolean {
    val timeDiff = System.currentTimeMillis() - lastFetchTimestamp
    return timeDiff > HOUR_IN_MILLIS
  }
}

fun interface FetchBitcoinRate {
  suspend operator fun invoke()
}