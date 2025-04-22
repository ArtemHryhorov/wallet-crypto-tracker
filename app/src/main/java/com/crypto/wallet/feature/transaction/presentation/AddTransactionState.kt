package com.crypto.wallet.feature.transaction.presentation

import com.crypto.wallet.feature.transaction.presentation.model.CategoryUiModel
import com.crypto.wallet.ui.common.TextUiModel

data class AddTransactionState(
  val isValidInput: Boolean,
  val inputValue: String,
  val allCategories: List<CategoryUiModel>,
  val selectedCategory: CategoryUiModel?,
  val errorMessage: TextUiModel?,
  val transactionAdded: Unit?,
) {
  val isTransactionAllowed: Boolean
    get() = isValidInput && inputValue.isNotBlank() && selectedCategory != null

  companion object {
    val loading = AddTransactionState(
      isValidInput = true,
      inputValue = "",
      allCategories = emptyList(),
      selectedCategory = null,
      errorMessage = null,
      transactionAdded = null,
    )
  }
}