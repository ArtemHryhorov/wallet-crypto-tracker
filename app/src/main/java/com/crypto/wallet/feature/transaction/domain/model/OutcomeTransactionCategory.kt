package com.crypto.wallet.feature.transaction.domain.model

sealed interface OutcomeTransactionCategory {
  data object Groceries : OutcomeTransactionCategory
  data object Taxi : OutcomeTransactionCategory
  data object Electronics : OutcomeTransactionCategory
  data object Restaurant : OutcomeTransactionCategory
  data object Other : OutcomeTransactionCategory

  companion object {
    val allCategories: List<OutcomeTransactionCategory> = listOf(
      Groceries,
      Taxi,
      Electronics,
      Restaurant,
      Other,
    )
  }
}