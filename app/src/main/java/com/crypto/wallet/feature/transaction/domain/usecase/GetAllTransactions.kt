package com.crypto.wallet.feature.transaction.domain.usecase

import com.crypto.wallet.feature.transaction.domain.model.TransactionsByDate
import com.crypto.wallet.feature.transaction.domain.repository.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZoneId
import javax.inject.Inject

class GetAllTransactionsUseCase @Inject constructor(
  private val repository: TransactionsRepository,
) : GetAllTransactions {
  override fun invoke(): Flow<Result<List<TransactionsByDate>>> =
    repository
      .getAllTransactions()
      .map { result ->
        result.map { transactionList ->
          transactionList
            .groupBy { it.creationDate.atZone(ZoneId.systemDefault()).toLocalDate() }
            .toSortedMap(compareByDescending { it })
            .map { (date, transactions) ->
              TransactionsByDate(date, transactions)
            }
        }
      }
}

fun interface GetAllTransactions {
  operator fun invoke(): Flow<Result<List<TransactionsByDate>>>
}