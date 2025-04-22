package com.crypto.wallet.feature.balance.domain.usecase

import com.crypto.wallet.feature.balance.domain.model.Balance
import com.crypto.wallet.feature.balance.domain.repository.BalanceRepository
import com.crypto.wallet.feature.transaction.domain.model.TransactionType
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ChangeBalanceUseCase @Inject constructor(
  private val repository: BalanceRepository,
) : ChangeBalance {
  override suspend operator fun invoke(
    transactionType: TransactionType,
    amount: Double,
  ): Result<Balance> {
    return repository
      .getUserBalance()
      .first()
      .onSuccess { currentBalance ->
        val updatedBalance = when (transactionType) {
          TransactionType.Income -> Balance(currentBalance.value + amount)
          is TransactionType.Outcome -> Balance(currentBalance.value - amount)
        }
        repository.updateUserBalance(updatedBalance)
      }
  }
}

fun interface ChangeBalance {
  suspend operator fun invoke(
    transactionType: TransactionType,
    amount: Double,
  ): Result<Balance>
}