package com.crypto.wallet.feature.transaction.domain.model

import java.time.Instant

data class CreateTransactionInput(
  val transactionType: TransactionType,
  val creationDate: Instant,
  val amount: Double,
)