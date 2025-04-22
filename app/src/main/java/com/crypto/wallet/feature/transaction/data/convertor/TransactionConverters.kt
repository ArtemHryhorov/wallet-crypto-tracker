package com.crypto.wallet.feature.transaction.data.convertor

import androidx.room.TypeConverter
import com.crypto.wallet.feature.transaction.data.entity.OutcomeTransactionCategoryEntity
import com.crypto.wallet.feature.transaction.data.entity.TransactionTypeEntity
import java.time.Instant

class TransactionConverters {
  @TypeConverter
  fun fromInstant(instant: Instant): Long = instant.toEpochMilli()

  @TypeConverter
  fun toInstant(millis: Long): Instant = Instant.ofEpochMilli(millis)

  @TypeConverter
  fun fromTransactionType(type: TransactionTypeEntity): String = type.name

  @TypeConverter
  fun toTransactionType(name: String): TransactionTypeEntity = TransactionTypeEntity.valueOf(name)

  @TypeConverter
  fun fromCategory(category: OutcomeTransactionCategoryEntity?): String? = category?.name

  @TypeConverter
  fun toCategory(name: String?): OutcomeTransactionCategoryEntity? =
    name?.let { OutcomeTransactionCategoryEntity.valueOf(it) }
}