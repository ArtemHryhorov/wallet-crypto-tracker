package com.crypto.wallet.feature.transaction.di

import com.crypto.wallet.feature.transaction.domain.usecase.GetTransactions
import com.crypto.wallet.feature.transaction.domain.usecase.GetTransactionsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TransactionModule {
  @Binds
  fun bindGetTransactions(
    useCase: GetTransactionsUseCase
  ): GetTransactions
}