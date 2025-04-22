package com.crypto.wallet.feature.balance.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency-rate")
data class CurrencyRateEntity(
  @PrimaryKey val id: Int = 1,
  val rate: Double,
  val timestamp: Long,
)