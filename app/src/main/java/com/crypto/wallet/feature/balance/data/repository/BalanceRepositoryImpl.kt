package com.crypto.wallet.feature.balance.data.repository

import com.crypto.wallet.core.dispatchers.IoDispatcher
import com.crypto.wallet.feature.balance.data.dao.UserBalanceDao
import com.crypto.wallet.feature.balance.data.mapper.toDomain
import com.crypto.wallet.feature.balance.data.mapper.toEntity
import com.crypto.wallet.feature.balance.domain.model.Balance
import com.crypto.wallet.feature.balance.domain.repository.BalanceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BalanceRepositoryImpl @Inject constructor(
  @IoDispatcher private val dispatcher: CoroutineDispatcher,
  private val balanceDao: UserBalanceDao,
) : BalanceRepository{
  override fun getUserBalance(): Flow<Result<Balance>> =
    balanceDao
      .getBalance()
      .transform { entity ->
        if (entity == null) {
          emit(Result.failure(NoSuchElementException("Balance not found")))
        } else {
          emit(Result.success(entity.toDomain()))
        }
      }
      .flowOn(dispatcher)

  override suspend fun updateUserBalance(balance: Balance): Result<Unit> =
    withContext(dispatcher) {
      try {
        balanceDao.insertOrUpdateBalance(balance.toEntity())
        Result.success(Unit)
      } catch (error: Exception) {
        Result.failure(error)
      }
    }
}