package com.crypto.wallet.feature.balance.domain.usecase

import com.crypto.wallet.feature.balance.domain.model.Balance
import com.crypto.wallet.feature.balance.domain.repository.BalanceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserBalanceUseCase @Inject constructor(
  private val repository: BalanceRepository,
) : GetUserBalance {
  override fun invoke(): Flow<Result<Balance>> = repository.getUserBalance()
}

fun interface GetUserBalance {
  operator fun invoke(): Flow<Result<Balance>>
}