package com.crypto.wallet.feature.balance.di

import com.crypto.wallet.core.database.AppDatabase
import com.crypto.wallet.feature.balance.data.dao.CurrencyRateDao
import com.crypto.wallet.feature.balance.data.dao.UserBalanceDao
import com.crypto.wallet.feature.balance.data.datasource.CurrencyRateLocalDatasourceImpl
import com.crypto.wallet.feature.balance.data.datasource.CurrencyRateRemoteDatasourceImpl
import com.crypto.wallet.feature.balance.data.repository.BalanceRepositoryImpl
import com.crypto.wallet.feature.balance.domain.datasource.CurrencyRateLocalDatasource
import com.crypto.wallet.feature.balance.domain.datasource.CurrencyRateRemoteDatasource
import com.crypto.wallet.feature.balance.domain.repository.BalanceRepository
import com.crypto.wallet.feature.balance.domain.usecase.CreateUserBalance
import com.crypto.wallet.feature.balance.domain.usecase.CreateUserBalanceUseCase
import com.crypto.wallet.feature.balance.domain.usecase.GetUserBalance
import com.crypto.wallet.feature.balance.domain.usecase.GetUserBalanceUseCase
import com.crypto.wallet.feature.balance.domain.usecase.IsValidAmount
import com.crypto.wallet.feature.balance.domain.usecase.IsValidAmountUseCase
import com.crypto.wallet.feature.balance.domain.usecase.ChangeBalance
import com.crypto.wallet.feature.balance.domain.usecase.ChangeBalanceUseCase
import com.crypto.wallet.feature.balance.domain.usecase.FetchBitcoinRate
import com.crypto.wallet.feature.balance.domain.usecase.FetchBitcoinRateUseCase
import com.crypto.wallet.feature.balance.domain.usecase.GetBtcRate
import com.crypto.wallet.feature.balance.domain.usecase.GetBtcRateUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BalanceModule {
  @Binds
  fun bindGetUserBalance(useCase: GetUserBalanceUseCase): GetUserBalance

  @Binds
  fun bindTopUpBalance(useCase: ChangeBalanceUseCase): ChangeBalance

  @Binds
  fun bindCreateUserBalance(useCase: CreateUserBalanceUseCase): CreateUserBalance

  @Binds
  fun bindIsValidAmount(useCase: IsValidAmountUseCase): IsValidAmount

  @Binds
  fun bindGetBtcRateUse(useCase: GetBtcRateUseCase): GetBtcRate

  @Binds
  fun bindFetchBitcoinRate(useCase: FetchBitcoinRateUseCase): FetchBitcoinRate

  @Binds
  fun bindCurrencyRateRemoteDatasource(datasource: CurrencyRateRemoteDatasourceImpl): CurrencyRateRemoteDatasource

  @Binds
  fun bindCurrencyRateLocalDatasource(datasource: CurrencyRateLocalDatasourceImpl): CurrencyRateLocalDatasource

  @Binds
  fun bindBalanceRepository(repository: BalanceRepositoryImpl): BalanceRepository

  companion object BalanceDaoModule {
    @Provides
    fun provideUserBalanceDao(db: AppDatabase): UserBalanceDao = db.userBalanceDao()

    @Provides
    fun provideCurrencyRateDao(db: AppDatabase): CurrencyRateDao = db.currencyRateDao()
  }
}