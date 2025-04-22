package com.crypto.wallet.feature.transaction.presentation.model

import com.crypto.wallet.ui.common.TextUiModel

sealed class TransactionTypeUiModel(val value: TextUiModel) {
  data class Income(val text: TextUiModel) : TransactionTypeUiModel(value = text)
  data class Outcome(val text: TextUiModel) : TransactionTypeUiModel(value = text)
}