package com.crypto.wallet.feature.transaction.domain.usecase

import com.crypto.wallet.feature.balance.domain.usecase.IsValidAmount
import javax.inject.Inject

class IsValidTransactionUseCase @Inject constructor(
  private val isValidAmount: IsValidAmount,
) : IsValidTransaction {
  override fun invoke(
    userBalance: Double,
    amount: String,
  ): Boolean {
    if (!isValidAmount(amount)) return false
    val transactionAmount = amount.toDoubleOrNull() ?: return false
    return transactionAmount <= userBalance
  }
}

fun interface IsValidTransaction {
  operator fun invoke(
    userBalance: Double,
    amount: String,
  ): Boolean
}