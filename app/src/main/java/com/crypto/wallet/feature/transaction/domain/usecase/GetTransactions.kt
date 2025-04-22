package com.crypto.wallet.feature.transaction.domain.usecase

import com.crypto.wallet.feature.transaction.domain.model.OutcomeTransactionCategory
import com.crypto.wallet.feature.transaction.domain.model.Transaction
import com.crypto.wallet.feature.transaction.domain.model.TransactionType
import com.crypto.wallet.feature.transaction.domain.model.TransactionsByDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor() : GetTransactions {
  override fun invoke(): Flow<Result<List<TransactionsByDate>>> {
    return flowOf(
      Result.success(
        listOf(
          Transaction(
            creationDate = Instant.now(),
            type = TransactionType.Income,
            amount = 250.0,
          ),
          Transaction(
            creationDate = Instant.now(),
            type = TransactionType.Outcome(
              category = OutcomeTransactionCategory.Taxi,
            ),
            amount = 150.0,
          ),
          Transaction(
            creationDate = Instant.now().plusSeconds(86400),
            type = TransactionType.Outcome(
              category = OutcomeTransactionCategory.Other,
            ),
            amount = 150.0,
          ),
          Transaction(
            creationDate = Instant.now().plusSeconds(86400),
            type = TransactionType.Income,
            amount = 450.0,
          ),
          Transaction(
            creationDate = Instant.now().plusSeconds(172800),
            type = TransactionType.Outcome(
              category = OutcomeTransactionCategory.Groceries,
            ),
            amount = 5.0,
          ),
          Transaction(
            creationDate = Instant.now(),
            type = TransactionType.Income,
            amount = 250.0,
          ),
          Transaction(
            creationDate = Instant.now(),
            type = TransactionType.Outcome(
              category = OutcomeTransactionCategory.Taxi,
            ),
            amount = 150.0,
          ),
          Transaction(
            creationDate = Instant.now().plusSeconds(86400),
            type = TransactionType.Outcome(
              category = OutcomeTransactionCategory.Other,
            ),
            amount = 150.0,
          ),
          Transaction(
            creationDate = Instant.now().plusSeconds(86400),
            type = TransactionType.Income,
            amount = 450.0,
          ),
          Transaction(
            creationDate = Instant.now().plusSeconds(172800),
            type = TransactionType.Outcome(
              category = OutcomeTransactionCategory.Groceries,
            ),
            amount = 5.0,
          ),
          Transaction(
            creationDate = Instant.now(),
            type = TransactionType.Income,
            amount = 250.0,
          ),
          Transaction(
            creationDate = Instant.now(),
            type = TransactionType.Outcome(
              category = OutcomeTransactionCategory.Taxi,
            ),
            amount = 150.0,
          ),
          Transaction(
            creationDate = Instant.now().plusSeconds(86400),
            type = TransactionType.Outcome(
              category = OutcomeTransactionCategory.Other,
            ),
            amount = 150.0,
          ),
          Transaction(
            creationDate = Instant.now().plusSeconds(86400),
            type = TransactionType.Income,
            amount = 450.0,
          ),
          Transaction(
            creationDate = Instant.now().plusSeconds(172800),
            type = TransactionType.Outcome(
              category = OutcomeTransactionCategory.Groceries,
            ),
            amount = 5.0,
          ),
        )
      )
    ).map { result ->
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
}

fun interface GetTransactions {
  operator fun invoke(): Flow<Result<List<TransactionsByDate>>>
}