package com.crypto.wallet.feature.balance.domain.usecase

import javax.inject.Inject

private const val MAX_TOP_UP_AMOUNT = 100_000
private const val ONLY_5_DIGITS_AFTER_DOT = """^\d+(\.\d{1,5})?$"""

class IsValidAmountUseCase @Inject constructor() : IsValidAmount {
  override fun invoke(amount: String): Boolean =
    amount
      .toDoubleOrNull()
      ?.let { value ->
        value > 0 &&
        value <= MAX_TOP_UP_AMOUNT &&
        amount.matches(Regex(ONLY_5_DIGITS_AFTER_DOT))
      } ?: false
}

fun interface IsValidAmount {
  operator fun invoke(amount: String): Boolean
}