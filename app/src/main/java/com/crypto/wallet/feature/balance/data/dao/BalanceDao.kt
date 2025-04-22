package com.crypto.wallet.feature.balance.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crypto.wallet.feature.balance.data.entity.BalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserBalanceDao {
  @Query("SELECT * FROM balance LIMIT 1")
  fun getBalance(): Flow<BalanceEntity?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrUpdateBalance(balance: BalanceEntity)
}