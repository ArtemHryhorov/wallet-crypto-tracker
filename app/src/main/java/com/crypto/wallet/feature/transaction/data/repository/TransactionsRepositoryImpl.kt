package com.crypto.wallet.feature.transaction.data.repository

import com.crypto.wallet.core.dispatchers.IoDispatcher
import com.crypto.wallet.feature.transaction.data.dao.TransactionDao
import com.crypto.wallet.feature.transaction.data.mapper.toDomain
import com.crypto.wallet.feature.transaction.data.mapper.toEntity
import com.crypto.wallet.feature.transaction.domain.model.CreateTransactionInput
import com.crypto.wallet.feature.transaction.domain.model.Transaction
import com.crypto.wallet.feature.transaction.domain.repository.TransactionsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
  @IoDispatcher private val dispatcher: CoroutineDispatcher,
  private val transactionDao: TransactionDao,
) : TransactionsRepository {
  override fun getAllTransactions(): Flow<Result<List<Transaction>>> =
    transactionDao
      .getAllTransactions()
      .map { entityList ->
        Result.success(entityList.map { it.toDomain() })
      }
      .catch { error ->
        emit(Result.failure(error))
      }
      .flowOn(dispatcher)

  override suspend fun createTransaction(input: CreateTransactionInput) =
    withContext(dispatcher) {
      try {
        transactionDao.insertTransaction(input.toEntity())
        Result.success(Unit)
      } catch (error: Exception) {
        Result.failure(error)
      }
    }
}