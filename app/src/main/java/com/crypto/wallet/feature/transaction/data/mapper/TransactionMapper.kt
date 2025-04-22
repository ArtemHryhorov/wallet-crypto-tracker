package com.crypto.wallet.feature.transaction.data.mapper

import com.crypto.wallet.feature.transaction.data.entity.OutcomeTransactionCategoryEntity
import com.crypto.wallet.feature.transaction.data.entity.TransactionEntity
import com.crypto.wallet.feature.transaction.data.entity.TransactionTypeEntity
import com.crypto.wallet.feature.transaction.domain.model.CreateTransactionInput
import com.crypto.wallet.feature.transaction.domain.model.OutcomeTransactionCategory
import com.crypto.wallet.feature.transaction.domain.model.Transaction
import com.crypto.wallet.feature.transaction.domain.model.TransactionType
import java.time.Instant

fun TransactionEntity.toDomain(): Transaction =
  Transaction(
    creationDate = creationDate,
    type = type.toDomain(category),
    amount = amount
  )

fun CreateTransactionInput.toEntity(): TransactionEntity =
  TransactionEntity(
    creationDate = Instant.now(),
    amount = amount,
    type = transactionType.toEntity(),
    category = (transactionType as? TransactionType.Outcome)?.category?.toEntity(),
  )

private fun TransactionTypeEntity.toDomain(
  categoryEntity: OutcomeTransactionCategoryEntity?
): TransactionType = when (this) {
  TransactionTypeEntity.INCOME -> TransactionType.Income
  TransactionTypeEntity.OUTCOME -> TransactionType.Outcome(
    category = categoryEntity
      ?.toDomain()
      ?: throw IllegalStateException("Outcome transaction must have a category") ,
  )
}

private fun TransactionType.toEntity(): TransactionTypeEntity =
  when (this) {
    TransactionType.Income -> TransactionTypeEntity.INCOME
    is TransactionType.Outcome -> TransactionTypeEntity.OUTCOME
  }

private fun OutcomeTransactionCategoryEntity.toDomain(): OutcomeTransactionCategory =
  when (this) {
    OutcomeTransactionCategoryEntity.ELECTRONICS -> OutcomeTransactionCategory.Electronics
    OutcomeTransactionCategoryEntity.GROCERIES -> OutcomeTransactionCategory.Groceries
    OutcomeTransactionCategoryEntity.OTHER -> OutcomeTransactionCategory.Other
    OutcomeTransactionCategoryEntity.RESTAURANT -> OutcomeTransactionCategory.Restaurant
    OutcomeTransactionCategoryEntity.TAXI -> OutcomeTransactionCategory.Taxi
  }

private fun OutcomeTransactionCategory.toEntity(): OutcomeTransactionCategoryEntity =
  when (this) {
    OutcomeTransactionCategory.Electronics -> OutcomeTransactionCategoryEntity.ELECTRONICS
    OutcomeTransactionCategory.Groceries -> OutcomeTransactionCategoryEntity.GROCERIES
    OutcomeTransactionCategory.Other -> OutcomeTransactionCategoryEntity.OTHER
    OutcomeTransactionCategory.Restaurant -> OutcomeTransactionCategoryEntity.RESTAURANT
    OutcomeTransactionCategory.Taxi -> OutcomeTransactionCategoryEntity.TAXI
  }