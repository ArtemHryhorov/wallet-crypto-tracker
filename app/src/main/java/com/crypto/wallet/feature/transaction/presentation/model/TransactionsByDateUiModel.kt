package com.crypto.wallet.feature.transaction.presentation.model

data class TransactionsByDateUiModel(
  val date: String,
  val transactions: List<TransactionUiModel>,
)