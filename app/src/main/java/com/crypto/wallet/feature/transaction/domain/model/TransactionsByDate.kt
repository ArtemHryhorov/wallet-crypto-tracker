package com.crypto.wallet.feature.transaction.domain.model

import java.time.LocalDate

data class TransactionsByDate(
  val date: LocalDate,
  val transactions: List<Transaction>,
)
