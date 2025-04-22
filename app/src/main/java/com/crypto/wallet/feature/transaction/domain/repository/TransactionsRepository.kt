package com.crypto.wallet.feature.transaction.domain.repository

import com.crypto.wallet.feature.transaction.domain.model.CreateTransactionInput
import com.crypto.wallet.feature.transaction.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {
  fun getAllTransactions(): Flow<Result<List<Transaction>>>

  suspend fun createTransaction(input: CreateTransactionInput): Result<Unit>
}