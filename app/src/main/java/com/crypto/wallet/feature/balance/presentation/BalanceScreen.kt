package com.crypto.wallet.feature.balance.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.crypto.wallet.R
import com.crypto.wallet.feature.balance.presentation.model.AmountUiModel
import com.crypto.wallet.feature.balance.presentation.top.up.TopUpDialog
import com.crypto.wallet.feature.transaction.presentation.model.TransactionTypeUiModel
import com.crypto.wallet.feature.transaction.presentation.model.TransactionUiModel
import com.crypto.wallet.ui.common.string
import com.crypto.wallet.ui.theme.Color
import com.crypto.wallet.ui.theme.CryptoWalletTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun BalanceRoute(
  onAddTransactionClick: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: BalanceViewModel = hiltViewModel(),
) {
  val state by viewModel.state.collectAsStateWithLifecycle()
  val transactions = viewModel.pagedTransactions.collectAsLazyPagingItems()

  BalanceScreen(
    transactions = transactions,
    state = state,
    actions = BalanceActions(
      onShowTopUpDialogClick = { viewModel.onEvent(BalanceEvent.ShowTopUpDialog) },
      onTopUpValueChange = { viewModel.onEvent(BalanceEvent.ValidateTopUpValue(it)) },
      onTopUpConfirm = { viewModel.onEvent(BalanceEvent.TopUpBalance(it)) },
      onTopUpDismiss = { viewModel.onEvent(BalanceEvent.DismissTopUpDialog) },
      onDismissError = { viewModel.onEvent(BalanceEvent.DismissError) },
      onAddTransactionClick = onAddTransactionClick,
    ),
    modifier = modifier,
  )
}

@Composable
private fun BalanceScreen(
  transactions: LazyPagingItems<TransactionUiModel>,
  state: BalanceState,
  actions: BalanceActions,
  modifier: Modifier = Modifier,
) {
  val snackbarHostState = remember { SnackbarHostState() }

  state.errorMessage?.let {
    val message = it.string()
    LaunchedEffect(it) {
      snackbarHostState.showSnackbar(message)
      actions.onDismissError()
    }
  }

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
        text = stringResource(R.string.balance_top_bar),
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
        transactions = transactions,
        state = state,
        actions = actions,
        modifier = Modifier.padding(paddingValues),
      )
    }
  }
}

@Composable
private fun BalanceScreenContent(
  transactions: LazyPagingItems<TransactionUiModel>,
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
    ExchangeRate(btcRate = state.btcRate)
    Spacer(modifier = Modifier.height(12.dp))
    UserBalanceCard(
      balance = state.balance,
      onTopUpClick = actions.onShowTopUpDialogClick,
      modifier = Modifier.fillMaxWidth(),
    )
    Spacer(modifier = Modifier.height(24.dp))
    Text(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(R.string.transactions_history),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.titleLarge,
    )
    Spacer(modifier = Modifier.height(8.dp))
    Button(
      modifier = Modifier
        .align(Alignment.End)
        .padding(end = 16.dp),
      onClick = actions.onAddTransactionClick,
    ) {
      Text(text = stringResource(R.string.add_transaction))
    }
    Spacer(modifier = Modifier.height(8.dp))
    TransactionsList(
      modifier = Modifier.fillMaxWidth(),
      transactions = transactions,
    )
  }
}

@Composable
private fun ExchangeRate(
  btcRate: String?,
  modifier: Modifier = Modifier,
) {
  Text(
    modifier = modifier,
    text = btcRate?.let {
      "1 BTC = $btcRate USD"
    } ?: run {
      stringResource(R.string.btc_rate_error_message)
    },
    style = MaterialTheme.typography.bodyMedium,
    color = MaterialTheme.colorScheme.onSurface,
  )
}

@Composable
private fun UserBalanceCard(
  balance: AmountUiModel?,
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
          text = balance.value.string(),
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
private fun TransactionsList(
  modifier: Modifier = Modifier,
  transactions: LazyPagingItems<TransactionUiModel>,
) {
  val grouped = transactions.itemSnapshotList.items
    .groupBy { it.creationDate }
    .toSortedMap(compareByDescending { it })

  LazyColumn(modifier = modifier) {
    grouped.forEach { (date, transactions) ->
      item {
        Text(
          modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
          text = date,
          textAlign = TextAlign.End,
          style = MaterialTheme.typography.labelMedium,
        )
      }
      itemsIndexed(transactions) { index, transaction ->
        TransactionItem(
          transaction = transaction,
          isFirst = index == 0,
          isLast = index == transactions.lastIndex,
        )
      }
    }

    when (transactions.loadState.append) {
      is LoadState.Loading -> item {
        CircularProgressIndicator(
          modifier = Modifier.padding(16.dp),
        )
      }
      is LoadState.Error -> item {
        Text(text = stringResource(R.string.failed_to_load_transactions_error_message))
      }
      is LoadState.NotLoading -> {
        // Nothing
      }
    }
  }
}

@Composable
private fun TransactionItem(
  isFirst: Boolean,
  isLast: Boolean,
  transaction: TransactionUiModel,
  modifier: Modifier = Modifier,
) {
  val backgroundColor = when (transaction.type) {
    is TransactionTypeUiModel.Outcome -> {
      if (isSystemInDarkTheme()) Color.DarkOutcome else Color.LightOutcome
    }

    is TransactionTypeUiModel.Income -> {
      if (isSystemInDarkTheme()) Color.DarkIncome else Color.LightIncome
    }
  }
  Row(
    modifier = modifier
      .background(
        color = backgroundColor,
        shape = RoundedCornerShape(
          topStart = if (isFirst) 16.dp else 0.dp,
          topEnd = if (isFirst) 16.dp else 0.dp,
          bottomStart = if (isLast) 16.dp else 0.dp,
          bottomEnd = if (isLast) 16.dp else 0.dp,
        )
      )
      .padding(horizontal = 12.dp)
      .padding(vertical = 6.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      modifier = Modifier.weight(1f),
      text = transaction.amount.value.string(),
      textAlign = TextAlign.Start,
      style = MaterialTheme.typography.titleLarge,
    )
    Spacer(modifier = Modifier.width(16.dp))
    Column(
      verticalArrangement = Arrangement.SpaceBetween,
      horizontalAlignment = Alignment.End,
    ) {
      Text(
        text = transaction.type.value.string(),
        textAlign = TextAlign.End,
        style = MaterialTheme.typography.labelLarge,
      )
      Spacer(modifier = Modifier.height(12.dp))
      Text(
        text = transaction.creationDate,
        style = MaterialTheme.typography.labelLarge,
      )
    }
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
      transactions = previewPagingItems(),
    )
  }
}

@Composable
fun previewPagingItems(): LazyPagingItems<TransactionUiModel> {
  val pagingFlow = remember { flowOf(PagingData.from(emptyList<TransactionUiModel>())) }
  return pagingFlow.collectAsLazyPagingItems()
}