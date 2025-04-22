package com.crypto.wallet.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.crypto.wallet.feature.balance.data.dao.UserBalanceDao
import com.crypto.wallet.feature.balance.data.entity.BalanceEntity

@Database(
  entities = [BalanceEntity::class],
  version = 1,
)
abstract class AppDatabase : RoomDatabase() {
  abstract fun userBalanceDao(): UserBalanceDao
}