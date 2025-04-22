package com.crypto.wallet.feature.balance.presentation

data class BalanceActions(
  val onShowTopUpDialogClick: () -> Unit,
  val onTopUpValueChange: (String) -> Unit,
  val onTopUpConfirm: (Double) -> Unit,
  val onTopUpDismiss: () -> Unit,
) {
  companion object {
    val Empty = BalanceActions(
      onShowTopUpDialogClick = {},
      onTopUpValueChange = {},
      onTopUpConfirm = {},
      onTopUpDismiss = {},
    )
  }
}