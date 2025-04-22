package com.crypto.wallet.feature.balance.data.datasource

import com.crypto.wallet.feature.balance.data.dao.CurrencyRateDao
import com.crypto.wallet.feature.balance.data.mapper.toDomain
import com.crypto.wallet.feature.balance.data.mapper.toEntity
import com.crypto.wallet.feature.balance.domain.datasource.CurrencyRateLocalDatasource
import com.crypto.wallet.feature.balance.domain.model.CurrencyRate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyRateLocalDatasourceImpl @Inject constructor(
  private val currencyRateDao: CurrencyRateDao,
) : CurrencyRateLocalDatasource {
  override fun getBitcoinRate(): Flow<Result<CurrencyRate>> =
    currencyRateDao
      .getCurrencyRate()
      .map { entity ->
        entity
          ?.toDomain()
          ?.let { Result.success(it) }
          ?: Result.failure(NoSuchElementException("Rate not found"))
      }

  override suspend fun updateBitcoinRate(rate: CurrencyRate) {
    currencyRateDao.insertOrUpdateCurrencyRate(rate.toEntity())
  }
}