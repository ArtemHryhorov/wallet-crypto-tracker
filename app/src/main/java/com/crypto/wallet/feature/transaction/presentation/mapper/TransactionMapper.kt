package com.crypto.wallet.feature.transaction.presentation.mapper

import com.crypto.wallet.R
import com.crypto.wallet.feature.balance.presentation.mapper.toAmountUiModel
import com.crypto.wallet.feature.transaction.domain.model.OutcomeTransactionCategory
import com.crypto.wallet.feature.transaction.domain.model.Transaction
import com.crypto.wallet.feature.transaction.domain.model.TransactionType
import com.crypto.wallet.feature.transaction.presentation.model.CategoryUiModel
import com.crypto.wallet.feature.transaction.presentation.model.TransactionTypeUiModel
import com.crypto.wallet.feature.transaction.presentation.model.TransactionUiModel
import com.crypto.wallet.ui.common.TextUiModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private const val TIME_PATTERN = "HH:mm"
private const val DATE_PATTERN = "yyyy-MM-dd"

fun OutcomeTransactionCategory.toUiModel(): CategoryUiModel =
  when(this) {
    OutcomeTransactionCategory.Electronics -> CategoryUiModel.Electronics
    OutcomeTransactionCategory.Groceries -> CategoryUiModel.Groceries
    OutcomeTransactionCategory.Other -> CategoryUiModel.Other
    OutcomeTransactionCategory.Restaurant -> CategoryUiModel.Restaurant
    OutcomeTransactionCategory.Taxi -> CategoryUiModel.Taxi
  }

fun CategoryUiModel.toDomain(): OutcomeTransactionCategory =
  when (this) {
    CategoryUiModel.Electronics -> OutcomeTransactionCategory.Electronics
    CategoryUiModel.Groceries -> OutcomeTransactionCategory.Groceries
    CategoryUiModel.Other -> OutcomeTransactionCategory.Other
    CategoryUiModel.Restaurant -> OutcomeTransactionCategory.Restaurant
    CategoryUiModel.Taxi -> OutcomeTransactionCategory.Taxi
  }

fun Transaction.toUiModel(): TransactionUiModel =
  TransactionUiModel(
    creationDate = creationDate.toUiDate(),
    creationTime = creationDate.toUiTime(),
    type = type.toUiModel(),
    amount = amount.toAmountUiModel()
  )

private fun Instant.toUiDate(): String {
  val formatter = DateTimeFormatter.ofPattern(DATE_PATTERN)
  val localDateTime = atZone(ZoneId.systemDefault())
  return formatter.format(localDateTime)
}

private fun Instant.toUiTime(): String {
  val formatter = DateTimeFormatter.ofPattern(TIME_PATTERN)
  val localDateTime = atZone(ZoneId.systemDefault())
  return formatter.format(localDateTime)
}

private fun TransactionType.toUiModel(): TransactionTypeUiModel =
  when(this) {
    TransactionType.Income -> TransactionTypeUiModel.Income(
      text = TextUiModel(R.string.income),
    )
    is TransactionType.Outcome -> TransactionTypeUiModel.Outcome(
      text = category.toUiModel().value,
    )
  }
