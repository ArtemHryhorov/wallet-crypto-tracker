package com.crypto.wallet.feature.balance.domain.usecase

import com.crypto.wallet.feature.balance.domain.model.Balance
import com.crypto.wallet.feature.balance.domain.repository.BalanceRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

// TODO - Add Unit tests

class TopUpBalanceUseCase @Inject constructor(
  private val repository: BalanceRepository,
) : TopUpBalance {
  override suspend operator fun invoke(amount: Double): Result<Balance> {
    return repository
      .getUserBalance()
      .first()
      .onSuccess { currentBalance ->
        val updatedBalance = Balance(currentBalance.value + amount)
        repository.updateUserBalance(updatedBalance)
      }
  }
}

fun interface TopUpBalance {
  suspend operator fun invoke(amount: Double): Result<Balance>
}