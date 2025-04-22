package com.crypto.wallet.feature.balance.domain.datasource

import com.crypto.wallet.feature.balance.domain.model.CurrencyRate

fun interface CurrencyRateRemoteDatasource {
  suspend fun getBitcoinRate(): Result<CurrencyRate>
}