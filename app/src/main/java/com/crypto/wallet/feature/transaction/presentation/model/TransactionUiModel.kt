package com.crypto.wallet.feature.transaction.presentation.model

import com.crypto.wallet.feature.balance.presentation.model.AmountUiModel

data class TransactionUiModel(
  val creationDate: String,
  val creationTime: String,
  val type: TransactionTypeUiModel,
  val amount: AmountUiModel,
)