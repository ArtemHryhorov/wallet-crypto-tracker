package com.crypto.wallet.feature.balance.presentation.mapper

import com.crypto.wallet.feature.balance.presentation.model.AmountUiModel
import com.crypto.wallet.ui.common.TextUiModel
import java.util.Locale

fun Double.toAmountUiModel(): AmountUiModel {
  val amount = String.format(Locale.ROOT, "%.5f", this)
  return AmountUiModel(
    value = TextUiModel(value = "$amount BTC")
  )
}