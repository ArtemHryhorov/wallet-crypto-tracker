package com.crypto.wallet.feature.balance.domain.datasource

import com.crypto.wallet.feature.balance.domain.model.CurrencyRate
import kotlinx.coroutines.flow.Flow

interface CurrencyRateLocalDatasource {
  fun getBitcoinRate(): Flow<Result<CurrencyRate>>

  suspend fun updateBitcoinRate(rate: CurrencyRate)
}