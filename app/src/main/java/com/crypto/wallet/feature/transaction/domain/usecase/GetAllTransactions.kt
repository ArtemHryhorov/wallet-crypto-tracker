package com.crypto.wallet.feature.transaction.domain.usecase

import androidx.paging.PagingData
import com.crypto.wallet.feature.transaction.domain.model.Transaction
import com.crypto.wallet.feature.transaction.domain.repository.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTransactionsUseCase @Inject constructor(
  private val repository: TransactionsRepository,
) : GetAllTransactions {
  override fun invoke(): Flow<PagingData<Transaction>> =
    repository.getAllTransactions()
}

fun interface GetAllTransactions {
  operator fun invoke(): Flow<PagingData<Transaction>>
}