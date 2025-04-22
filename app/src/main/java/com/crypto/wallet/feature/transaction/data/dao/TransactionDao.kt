package com.crypto.wallet.feature.transaction.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crypto.wallet.feature.transaction.data.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
  @Query("SELECT * FROM transactions ORDER BY creationDate DESC")
  fun getAllTransactions(): Flow<List<TransactionEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTransaction(transaction: TransactionEntity)
}