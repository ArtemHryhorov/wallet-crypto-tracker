package com.crypto.wallet.feature.transaction.domain.usecase

import com.crypto.wallet.feature.transaction.domain.model.OutcomeTransactionCategory
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor() : GetCategories {
  override fun invoke(): List<OutcomeTransactionCategory> =
    OutcomeTransactionCategory.allCategories
}

fun interface GetCategories {
  operator fun invoke(): List<OutcomeTransactionCategory>
}

