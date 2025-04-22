package com.crypto.wallet.feature.transaction.di

import com.crypto.wallet.core.data.AppDatabase
import com.crypto.wallet.feature.transaction.data.dao.TransactionDao
import com.crypto.wallet.feature.transaction.data.repository.TransactionsRepositoryImpl
import com.crypto.wallet.feature.transaction.domain.repository.TransactionsRepository
import com.crypto.wallet.feature.transaction.domain.usecase.AddTransaction
import com.crypto.wallet.feature.transaction.domain.usecase.AddTransactionUseCase
import com.crypto.wallet.feature.transaction.domain.usecase.GetAllTransactions
import com.crypto.wallet.feature.transaction.domain.usecase.GetAllTransactionsUseCase
import com.crypto.wallet.feature.transaction.domain.usecase.GetCategories
import com.crypto.wallet.feature.transaction.domain.usecase.GetCategoriesUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TransactionModule {
  @Binds
  fun bindGetCategories(
    useCase: GetCategoriesUseCase
  ): GetCategories

  @Binds
  fun bindAddTransaction(
    useCase: AddTransactionUseCase
  ): AddTransaction

  @Binds
  fun bindGetAllTransactions(
    useCase: GetAllTransactionsUseCase
  ): GetAllTransactions

  @Binds
  fun bindTransactionsRepository(
    repository: TransactionsRepositoryImpl
  ): TransactionsRepository

  companion object TransactionDaoModule {
    @Provides
    fun provideTransactionDao(db: AppDatabase): TransactionDao = db.transactionDao()
  }
}