package com.crypto.wallet.feature.balance.presentation.model

import com.crypto.wallet.feature.balance.domain.model.Balance
import com.crypto.wallet.ui.common.TextUiModel
import java.util.Locale

data class BalanceUiModel(
  val amount: TextUiModel,
) {
  companion object {
    fun from(balance: Balance): BalanceUiModel {
      val amount = String.format(Locale.ROOT, "%.5f", balance.value)
      return BalanceUiModel(
        amount = TextUiModel(value = "$amount BTC")
      )
    }
  }
}