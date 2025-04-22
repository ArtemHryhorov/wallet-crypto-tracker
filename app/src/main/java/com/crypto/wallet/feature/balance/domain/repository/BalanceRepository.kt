package com.crypto.wallet.feature.balance.domain.repository

import com.crypto.wallet.feature.balance.domain.model.Balance
import kotlinx.coroutines.flow.Flow

interface BalanceRepository {
  fun getUserBalance(): Flow<Result<Balance>>

  suspend fun updateUserBalance(balance: Balance): Result<Unit>
}