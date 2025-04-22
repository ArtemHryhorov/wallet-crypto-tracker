package com.crypto.wallet.feature.transaction.domain.usecase

import com.crypto.wallet.feature.balance.domain.usecase.GetUserBalance
import com.crypto.wallet.feature.balance.domain.usecase.IsValidAmount
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class IsValidTransactionUseCase @Inject constructor(
  private val isValidAmount: IsValidAmount,
  private val getUserBalance: GetUserBalance,
) : IsValidTransaction {
  override suspend fun invoke(amount: String): Boolean {
    if (!isValidAmount(amount)) return false
    val userBalance = getUserBalance().first().getOrNull() ?: return false
    val transactionAmount = amount.toDoubleOrNull() ?: return false
    return transactionAmount <= userBalance.value
  }
}

fun interface IsValidTransaction {
  suspend operator fun invoke(amount: String): Boolean
}