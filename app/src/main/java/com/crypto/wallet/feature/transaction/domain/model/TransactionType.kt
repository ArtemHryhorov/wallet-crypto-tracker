package com.crypto.wallet.feature.transaction.domain.model

sealed interface TransactionType {
  data object Income : TransactionType
  data class Outcome(val category: OutcomeTransactionCategory) : TransactionType
}
