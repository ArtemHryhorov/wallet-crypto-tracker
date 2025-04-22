package com.crypto.wallet.feature.balance.di

import com.crypto.wallet.core.database.AppDatabase
import com.crypto.wallet.feature.balance.data.dao.UserBalanceDao
import com.crypto.wallet.feature.balance.data.repository.BalanceRepositoryImpl
import com.crypto.wallet.feature.balance.domain.repository.BalanceRepository
import com.crypto.wallet.feature.balance.domain.usecase.CreateUserBalance
import com.crypto.wallet.feature.balance.domain.usecase.CreateUserBalanceUseCase
import com.crypto.wallet.feature.balance.domain.usecase.GetUserBalance
import com.crypto.wallet.feature.balance.domain.usecase.GetUserBalanceUseCase
import com.crypto.wallet.feature.balance.domain.usecase.IsValidAmount
import com.crypto.wallet.feature.balance.domain.usecase.IsValidAmountUseCase
import com.crypto.wallet.feature.balance.domain.usecase.ChangeBalance
import com.crypto.wallet.feature.balance.domain.usecase.ChangeBalanceUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BalanceModule {
  @Binds
  fun bindGetUserBalance(
    useCase: GetUserBalanceUseCase
  ): GetUserBalance

  @Binds
  fun bindTopUpBalance(
    useCase: ChangeBalanceUseCase
  ): ChangeBalance

  @Binds
  fun bindCreateUserBalance(
    useCase: CreateUserBalanceUseCase
  ): CreateUserBalance

  @Binds
  fun bindIsValidAmount(
    useCase: IsValidAmountUseCase
  ): IsValidAmount

  @Binds
  fun bindBalanceRepository(
    repository: BalanceRepositoryImpl
  ): BalanceRepository

  companion object BalanceDaoModule {
    @Provides
    fun provideUserBalanceDao(db: AppDatabase): UserBalanceDao = db.userBalanceDao()
  }
}