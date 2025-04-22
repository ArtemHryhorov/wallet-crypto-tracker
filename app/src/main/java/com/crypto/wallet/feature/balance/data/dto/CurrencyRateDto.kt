package com.crypto.wallet.feature.balance.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRateDto(
  val data: CoinData,
  val timestamp: Long
)

@Serializable
data class CoinData(
  val priceUsd: String
)