package com.crypto.wallet.feature.balance.domain.usecase

import javax.inject.Inject

// TODO - Add Unit tests

private const val MAX_TOP_UP_AMOUNT = 100_000
private const val ONLY_5_DIGITS_AFTER_DOT = """^\d+(\.\d{1,5})?$"""

class IsValidTopUpAmountUseCase @Inject constructor() : IsValidTopUpAmount {
  override fun invoke(amount: String): Boolean =
    amount
      .toDoubleOrNull()
      ?.let { value ->
        value > 0 &&
        value <= MAX_TOP_UP_AMOUNT &&
        amount.matches(Regex(ONLY_5_DIGITS_AFTER_DOT))
      } ?: false
}

fun interface IsValidTopUpAmount {
  operator fun invoke(amount: String): Boolean
}