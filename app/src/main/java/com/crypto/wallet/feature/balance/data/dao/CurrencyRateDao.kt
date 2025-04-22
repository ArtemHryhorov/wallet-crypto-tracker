package com.crypto.wallet.feature.balance.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crypto.wallet.feature.balance.data.entity.CurrencyRateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyRateDao {
  @Query("SELECT * FROM `currency-rate` LIMIT 1")
  fun getCurrencyRate(): Flow<CurrencyRateEntity?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrUpdateCurrencyRate(rate: CurrencyRateEntity)
}