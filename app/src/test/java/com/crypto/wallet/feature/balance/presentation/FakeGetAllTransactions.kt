package com.crypto.wallet.feature.balance.presentation

import androidx.paging.PagingData
import com.crypto.wallet.feature.transaction.domain.model.Transaction
import com.crypto.wallet.feature.transaction.domain.model.TransactionType
import com.crypto.wallet.feature.transaction.domain.usecase.GetAllTransactions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.Instant

class FakeGetAllTransactions : GetAllTransactions {
  override fun invoke(): Flow<PagingData<Transaction>> = flowOf(
    PagingData.from(
      listOf(
        Transaction(
          creationDate = Instant.now(),
          type = TransactionType.Income,
          amount = 100.0
        )
      )
    )
  )
}