package com.crypto.wallet.feature.transaction.domain.model

import java.time.Instant

data class Transaction(
  val creationDate: Instant,
  val type: TransactionType,
  val amount: Double,
)
