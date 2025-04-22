package com.crypto.wallet.feature.balance.presentation

sealed interface BalanceEvent {
  data object ShowTopUpDialog : BalanceEvent
  data class ValidateTopUpValue(val value: String) : BalanceEvent
  data class TopUpBalance(val amount: Double) : BalanceEvent
  data object DismissTopUpDialog : BalanceEvent
}