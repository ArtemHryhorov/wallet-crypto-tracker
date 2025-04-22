package com.crypto.wallet.feature.balance.data.mapper

import com.crypto.wallet.feature.balance.data.entity.CurrencyRateEntity
import com.crypto.wallet.feature.balance.domain.model.CurrencyRate

fun CurrencyRateEntity.toDomain(): CurrencyRate =
  CurrencyRate(
    rate = rate,
    timestamp = timestamp,
  )

fun CurrencyRate.toEntity(): CurrencyRateEntity =
  CurrencyRateEntity(
    rate = rate,
    timestamp = timestamp,
  )