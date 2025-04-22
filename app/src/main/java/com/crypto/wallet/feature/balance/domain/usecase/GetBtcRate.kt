@file:OptIn(ExperimentalCoroutinesApi::class)

package com.crypto.wallet.feature.balance.domain.usecase

import com.crypto.wallet.feature.balance.domain.datasource.CurrencyRateLocalDatasource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import java.util.Locale
import javax.inject.Inject

class GetBtcRateUseCase @Inject constructor(
  private val localDatasource: CurrencyRateLocalDatasource,
) : GetBtcRate {
  override fun invoke(): Flow<Result<String>> =
    localDatasource
      .getBitcoinRate()
      .mapLatest { result ->
        result.map { String.format(Locale.ROOT, "%.5f", it.rate) }
      }
}

fun interface GetBtcRate {
  operator fun invoke(): Flow<Result<String>>
}