package com.crypto.wallet.feature.balance.presentation.top.up

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.crypto.wallet.R
import com.crypto.wallet.ui.theme.CryptoWalletTheme

@Composable
fun TopUpDialog(
  value: String,
  isValid: Boolean,
  onValueChange: (String) -> Unit,
  onConfirm: (Double) -> Unit,
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier,
) {
  AlertDialog(
    modifier = modifier,
    onDismissRequest = onDismiss,
    confirmButton = {
      Button(
        onClick = {
          value.toDoubleOrNull()?.let { onConfirm(it) }
        },
        enabled = isValid && value.isNotBlank(),
      ) {
        Text(text = stringResource(R.string.confirm))
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text(text = stringResource(R.string.cancel))
      }
    },
    title = {
      Text(text = stringResource(R.string.top_up_balance))
    },
    text = {
      OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Number,
        ),
        label = {
          Text(text = stringResource(R.string.amount))
        },
        supportingText = {
          if (!isValid && value.isNotBlank()) {
            Text(
              modifier = Modifier.fillMaxWidth(),
              text = stringResource(R.string.top_up_validation_error_message),
              color = MaterialTheme.colorScheme.error,
            )
          }
        },
        isError = !isValid,
      )
    }
  )
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PreviewTopUpDialog() {
  CryptoWalletTheme {
    TopUpDialog(
      value = "250",
      isValid = true,
      onValueChange = {},
      onConfirm = {},
      onDismiss = {},
    )
  }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PreviewTopUpDialogError() {
  CryptoWalletTheme {
    TopUpDialog(
      value = "5,6.2,,4",
      isValid = false,
      onValueChange = {},
      onConfirm = {},
      onDismiss = {},
    )
  }
}