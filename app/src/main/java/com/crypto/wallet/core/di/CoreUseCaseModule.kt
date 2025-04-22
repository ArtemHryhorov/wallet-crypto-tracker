package com.crypto.wallet.core.di

import com.crypto.wallet.core.domain.IsValidAmount
import com.crypto.wallet.core.domain.IsValidAmountUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoreUseCaseModule {
  @Binds
  fun bindIsValidAmount(
    useCase: IsValidAmountUseCase
  ): IsValidAmount
}