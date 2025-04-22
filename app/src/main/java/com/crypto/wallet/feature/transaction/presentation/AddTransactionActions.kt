package com.crypto.wallet.feature.transaction.presentation

import com.crypto.wallet.feature.transaction.presentation.model.CategoryUiModel

data class AddTransactionActions(
  val onValueChange: (String) -> Unit,
  val onCategoryClick: (CategoryUiModel) -> Unit,
  val onAddTransactionClick: (Double) -> Unit,
  val onDismissError: () -> Unit,
  val onNavigateBackClick: () -> Unit,
) {
  companion object {
    val Empty = AddTransactionActions(
      onValueChange = {},
      onCategoryClick = {},
      onAddTransactionClick = {},
      onDismissError = {},
      onNavigateBackClick = {},
    )
  }
}
