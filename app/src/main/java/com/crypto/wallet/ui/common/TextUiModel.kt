package com.crypto.wallet.ui.common

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource

sealed interface TextUiModel {
  data class Text(val value: String) : TextUiModel
  data class TextRes(@StringRes val value: Int) : TextUiModel
}

fun TextUiModel(value: String): TextUiModel = TextUiModel.Text(value)

fun TextUiModel(@StringRes value: Int): TextUiModel = TextUiModel.TextRes(value)

@Composable
@ReadOnlyComposable
fun TextUiModel.string() = when (this) {
  is TextUiModel.Text -> value
  is TextUiModel.TextRes -> stringResource(value)
}