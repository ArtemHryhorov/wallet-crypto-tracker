package com.crypto.wallet.feature.transaction.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "transactions")
data class TransactionEntity(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  val creationDate: Instant,
  val amount: Double,
  val type: TransactionTypeEntity,
  val category: OutcomeTransactionCategoryEntity?
)
