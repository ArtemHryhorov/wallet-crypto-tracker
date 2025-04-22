package com.crypto.wallet.feature.transaction.domain.usecase

import com.crypto.wallet.feature.transaction.domain.model.CreateTransactionInput
import com.crypto.wallet.feature.transaction.domain.repository.TransactionsRepository
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
  private val repository: TransactionsRepository,
) : AddTransaction {
  override suspend fun invoke(input: CreateTransactionInput): Result<Unit> =
    repository.createTransaction(input)
}

fun interface AddTransaction {
  suspend operator fun invoke(input: CreateTransactionInput): Result<Unit>
}