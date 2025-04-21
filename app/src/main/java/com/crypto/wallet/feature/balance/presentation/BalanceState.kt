package com.crypto.wallet.feature.balance.presentation

data class BalanceState(
  val balance: String?,
) {
  companion object {
    val loading = BalanceState(
      balance = null,
    )
  }
}
