package com.crypto.wallet.feature.transaction.presentation

import com.crypto.wallet.feature.transaction.presentation.model.CategoryUiModel

sealed interface AddTransactionEvent {
  data class ValidateInputValue(val value: String) : AddTransactionEvent
  data class ProcessCategoryClick(val category: CategoryUiModel) : AddTransactionEvent
  data class AddTransaction(val amount: Double) : AddTransactionEvent
  data object DismissError : AddTransactionEvent
}