package com.crypto.wallet.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.crypto.wallet.feature.balance.data.dao.CurrencyRateDao
import com.crypto.wallet.feature.balance.data.dao.UserBalanceDao
import com.crypto.wallet.feature.balance.data.entity.BalanceEntity
import com.crypto.wallet.feature.balance.data.entity.CurrencyRateEntity
import com.crypto.wallet.feature.transaction.data.convertor.TransactionConverters
import com.crypto.wallet.feature.transaction.data.dao.TransactionDao
import com.crypto.wallet.feature.transaction.data.entity.TransactionEntity

@Database(
  entities = [
    BalanceEntity::class,
    TransactionEntity::class,
    CurrencyRateEntity::class,
  ],
  version = 1,
)
@TypeConverters(TransactionConverters::class)
abstract class AppDatabase : RoomDatabase() {
  abstract fun userBalanceDao(): UserBalanceDao
  abstract fun transactionDao(): TransactionDao
  abstract fun currencyRateDao(): CurrencyRateDao
}