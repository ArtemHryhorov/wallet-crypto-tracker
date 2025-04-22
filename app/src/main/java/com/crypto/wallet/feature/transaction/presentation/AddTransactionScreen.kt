@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.crypto.wallet.feature.transaction.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.crypto.wallet.R
import com.crypto.wallet.feature.transaction.domain.model.OutcomeTransactionCategory
import com.crypto.wallet.feature.transaction.presentation.mapper.toUiModel
import com.crypto.wallet.feature.transaction.presentation.model.CategoryUiModel
import com.crypto.wallet.ui.common.string
import com.crypto.wallet.ui.theme.CryptoWalletTheme

@Composable
fun AddTransactionRoute(
  onNavigateBackClick: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: AddTransactionViewModel = hiltViewModel(),
) {
  val focusManager = LocalFocusManager.current
  val state by viewModel.state.collectAsStateWithLifecycle()

  state.transactionAdded?.let {
    LaunchedEffect(Unit) {
      focusManager.clearFocus(force = true)
      onNavigateBackClick()
    }
  }

  AddTransactionScreen(
    modifier = modifier,
    state = state,
    actions = AddTransactionActions(
      onValueChange = { viewModel.onEvent(AddTransactionEvent.ValidateInputValue(it)) },
      onCategoryClick = { viewModel.onEvent(AddTransactionEvent.ProcessCategoryClick(it)) },
      onAddTransactionClick = { viewModel.onEvent(AddTransactionEvent.AddTransaction(it)) },
      onNavigateBackClick = {
        focusManager.clearFocus(force = true)
        onNavigateBackClick()
      },
    )
  )
}

@Composable
private fun AddTransactionScreen(
  actions: AddTransactionActions,
  state: AddTransactionState,
  modifier: Modifier = Modifier,
) {
  val snackbarHostState = remember { SnackbarHostState() }

  // TODO - Add error snackbar

  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(
        colors = TopAppBarColors(
          containerColor = MaterialTheme.colorScheme.background,
          scrolledContainerColor = MaterialTheme.colorScheme.background,
          navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
          titleContentColor = MaterialTheme.colorScheme.onBackground,
          actionIconContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        title = {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.add_transaction_top_bar),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
          )
        },
        navigationIcon = {
          IconButton(onClick = actions.onNavigateBackClick) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = stringResource(R.string.back),
            )
          }
        }
      )
    },
    snackbarHost = {
      SnackbarHost(hostState = snackbarHostState)
    },
    containerColor = MaterialTheme.colorScheme.background,
  ) { paddingValues ->
    Surface(color = MaterialTheme.colorScheme.background) {
      AddTransactionScreenContent(
        state = state,
        actions = actions,
        modifier = Modifier.padding(paddingValues),
      )
    }
  }
}

@Composable
private fun AddTransactionScreenContent(
  state: AddTransactionState,
  actions: AddTransactionActions,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier,
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Max),
    ) {
      OutlinedTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 24.dp),
        value = state.inputValue,
        onValueChange = actions.onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Number,
        ),
        label = {
          Text(text = stringResource(R.string.amount))
        },
        supportingText = {
          if (!state.isValidInput && state.inputValue.isNotBlank()) {
            Text(
              modifier = Modifier.fillMaxWidth(),
              text = stringResource(R.string.transaction_validation_error_message),
              color = MaterialTheme.colorScheme.error,
            )
          }
        },
        isError = !state.isValidInput,
      )
    }
    Spacer(modifier = Modifier.height(12.dp))
    FlowRow(
      modifier = Modifier.padding(horizontal = 20.dp),
    ) {
      state.allCategories.forEach { category ->
        CategoryItem(
          category = category,
          isSelected = category == state.selectedCategory,
          onClick = actions.onCategoryClick,
        )
      }
    }
    Spacer(modifier = Modifier.weight(1f))
    Button(
      modifier = Modifier
        .fillMaxWidth()
        .imePadding()
        .padding(horizontal = 24.dp)
        .padding(bottom = 12.dp),
      onClick = {
        state.inputValue.toDoubleOrNull()?.let {
          actions.onAddTransactionClick(it)
        }
      },
      enabled = state.isTransactionAllowed,
    ) {
      Text(text = stringResource(R.string.add_transaction))
    }
  }
}

@Composable
private fun CategoryItem(
  category: CategoryUiModel,
  isSelected: Boolean,
  onClick: (CategoryUiModel) -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier
      .padding(horizontal = 8.dp)
      .padding(vertical = 4.dp)
      .background(
        color = if (isSelected) {
          MaterialTheme.colorScheme.primary
        } else {
          MaterialTheme.colorScheme.surfaceContainerLow
        },
        shape = RoundedCornerShape(16.dp),
      )
      .clickable { onClick(category) }
  ) {
    Text(
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .padding(vertical = 4.dp),
      text = category.value.string(),
      color = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
      } else {
        MaterialTheme.colorScheme.onSurfaceVariant
      },
    )
  }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PreviewAddTransactionScreen() {
  CryptoWalletTheme {
    AddTransactionScreen(
      actions = AddTransactionActions.Empty,
      state = AddTransactionState.loading.copy(
        allCategories = OutcomeTransactionCategory.allCategories.map { it.toUiModel() },
        selectedCategory = CategoryUiModel.Taxi,
      ),
    )
  }
}