package com.crypto.wallet.feature.transaction.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crypto.wallet.feature.transaction.data.entity.TransactionEntity

@Dao
interface TransactionDao {
  @Query("SELECT * FROM transactions ORDER BY creationDate DESC")
  fun getAllTransactions(): PagingSource<Int, TransactionEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertTransaction(transaction: TransactionEntity)
}