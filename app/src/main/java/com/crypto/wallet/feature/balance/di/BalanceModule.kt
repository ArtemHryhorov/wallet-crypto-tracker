package com.crypto.wallet.feature.balance.di

import com.crypto.wallet.feature.balance.domain.GetUserBalance
import com.crypto.wallet.feature.balance.domain.GetUserBalanceUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
fun interface BalanceModule {
  @Binds
  fun bindGetUserBalance(
    useCase: GetUserBalanceUseCase
  ): GetUserBalance
}