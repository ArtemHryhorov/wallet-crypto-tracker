package com.crypto.wallet.feature.balance.presentation

import com.crypto.wallet.feature.balance.presentation.model.BalanceUiModel
import com.crypto.wallet.ui.common.TextUiModel

data class BalanceState(
  val balance: BalanceUiModel?,
  val showTopUpDialog: Boolean,
  val topUpDialogValue: String,
  val isTopUpDialogValueValid: Boolean,
  val errorMessage: TextUiModel?,
) {
  companion object {
    val loading = BalanceState(
      balance = null,
      showTopUpDialog = false,
      topUpDialogValue = "",
      isTopUpDialogValueValid = true,
      errorMessage = null,
    )
  }
}
