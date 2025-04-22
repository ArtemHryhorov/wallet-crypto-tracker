package com.crypto.wallet.feature.balance.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.crypto.wallet.R
import com.crypto.wallet.feature.balance.presentation.model.BalanceUiModel
import com.crypto.wallet.feature.balance.presentation.top.up.TopUpDialog
import com.crypto.wallet.ui.common.TextUiModel
import com.crypto.wallet.ui.common.string
import com.crypto.wallet.ui.theme.CryptoWalletTheme

@Composable
fun BalanceRoute(
  modifier: Modifier = Modifier,
  viewModel: BalanceViewModel = hiltViewModel(),
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  BalanceScreen(
    state = state,
    actions = BalanceActions(
      onShowTopUpDialogClick = { viewModel.onEvent(BalanceEvent.ShowTopUpDialog) },
      onTopUpValueChange = { viewModel.onEvent(BalanceEvent.ValidateTopUpValue(it)) },
      onTopUpConfirm = { viewModel.onEvent(BalanceEvent.TopUpBalance(it)) },
      onTopUpDismiss = { viewModel.onEvent(BalanceEvent.DismissTopUpDialog) },
    ),
    modifier = modifier,
  )
}

@Composable
private fun BalanceScreen(
  state: BalanceState,
  actions: BalanceActions,
  modifier: Modifier = Modifier,
) {
  val snackbarHostState = remember { SnackbarHostState() }

  if (state.showTopUpDialog) {
    TopUpDialog(
      value = state.topUpDialogValue,
      isValid = state.isTopUpDialogValueValid,
      onValueChange = actions.onTopUpValueChange,
      onConfirm = actions.onTopUpConfirm,
      onDismiss = actions.onTopUpDismiss,
    )
  }

  Scaffold(
    modifier = modifier,
    topBar = {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.wallet_top_bar),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
      )
    },
    snackbarHost = {
      SnackbarHost(hostState = snackbarHostState)
    },
    containerColor = MaterialTheme.colorScheme.background,
  ) { paddingValues ->
    Surface(color = MaterialTheme.colorScheme.background) {
      BalanceScreenContent(
        state = state,
        actions = actions,
        modifier = Modifier.padding(paddingValues),
      )
    }
  }
}

@Composable
private fun BalanceScreenContent(
  state: BalanceState,
  actions: BalanceActions,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .padding(top = 8.dp)
      .padding(horizontal = 16.dp),
    horizontalAlignment = Alignment.End,
  ) {
    ExchangeRate()
    Spacer(modifier = Modifier.height(12.dp))
    UserBalanceCard(
      balance = state.balance,
      onTopUpClick = actions.onShowTopUpDialogClick,
      modifier = Modifier.fillMaxWidth(),
    )
  }
}

@Composable
private fun ExchangeRate(
  modifier: Modifier = Modifier,
) {
  Text(
    modifier = modifier,
    text = "1 BTC = 87108.93 USD",
    style = MaterialTheme.typography.bodyMedium,
    color = MaterialTheme.colorScheme.onSurface,
  )
}

@Composable
private fun UserBalanceCard(
  balance: BalanceUiModel?,
  onTopUpClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Card(
    modifier = modifier.padding(bottom = 12.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface,
    ),
    elevation = CardDefaults.cardElevation(
      defaultElevation = 6.dp
    ),
  ) {
    balance?.let {
      Column(
        modifier = Modifier
          .padding(horizontal = 16.dp)
          .padding(vertical = 8.dp),
      ) {
        Text(
          modifier = Modifier.fillMaxWidth(),
          text = balance.amount.string(),
          textAlign = TextAlign.Start,
          style = MaterialTheme.typography.displaySmall
        )
        Box(
          modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        ) {
          Button(
            modifier = Modifier
              .padding(top = 6.dp)
              .align(Alignment.CenterEnd),
            onClick = onTopUpClick,
          ) {
            Text(text = stringResource(R.string.top_up_balance))
          }
        }
      }
    } ?: run {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 32.dp)
      ) {
        CircularProgressIndicator(
          modifier = Modifier
            .align(Alignment.Center)
            .size(48.dp),
        )
      }
    }
  }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PreviewBalanceScreen() {
  CryptoWalletTheme {
    BalanceScreen(
      state = BalanceState.loading.copy(
        balance = BalanceUiModel(amount = TextUiModel("2.56246 BTC")),
      ),
      actions = BalanceActions.Empty,
    )
  }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PreviewBalanceScreenLoading() {
  CryptoWalletTheme {
    BalanceScreen(
      state = BalanceState.loading,
      actions = BalanceActions.Empty,
    )
  }
}