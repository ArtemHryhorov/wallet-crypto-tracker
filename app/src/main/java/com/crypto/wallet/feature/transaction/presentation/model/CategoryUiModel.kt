package com.crypto.wallet.feature.transaction.presentation.model

import com.crypto.wallet.R
import com.crypto.wallet.ui.common.TextUiModel

sealed class CategoryUiModel(val value: TextUiModel) {
  data object Groceries : CategoryUiModel(value = TextUiModel(R.string.category_groceries))
  data object Taxi : CategoryUiModel(value = TextUiModel(R.string.category_taxi))
  data object Electronics : CategoryUiModel(value = TextUiModel(R.string.category_electronics))
  data object Restaurant : CategoryUiModel(value = TextUiModel(R.string.category_restaurant))
  data object Other : CategoryUiModel(value = TextUiModel(R.string.category_other))
}