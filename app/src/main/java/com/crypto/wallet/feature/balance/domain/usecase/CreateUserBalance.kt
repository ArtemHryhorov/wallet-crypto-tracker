package com.crypto.wallet.feature.balance.domain.usecase

import com.crypto.wallet.feature.balance.domain.model.Balance
import com.crypto.wallet.feature.balance.domain.repository.BalanceRepository
import javax.inject.Inject

class CreateUserBalanceUseCase @Inject constructor(
  private val repository: BalanceRepository,
) : CreateUserBalance{
  override suspend fun invoke() {
    repository.updateUserBalance(Balance(0.0))
  }
}

fun interface CreateUserBalance {
  suspend operator fun invoke()
}