package com.crypto.wallet.feature.transaction.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.wallet.R
import com.crypto.wallet.core.dispatchers.IoDispatcher
import com.crypto.wallet.feature.balance.domain.usecase.ChangeBalance
import com.crypto.wallet.feature.balance.domain.usecase.GetUserBalance
import com.crypto.wallet.feature.transaction.domain.model.CreateTransactionInput
import com.crypto.wallet.feature.transaction.domain.model.OutcomeTransactionCategory
import com.crypto.wallet.feature.transaction.domain.model.TransactionType
import com.crypto.wallet.feature.transaction.domain.usecase.AddTransaction
import com.crypto.wallet.feature.transaction.domain.usecase.GetCategories
import com.crypto.wallet.feature.transaction.domain.usecase.IsValidTransaction
import com.crypto.wallet.feature.transaction.presentation.mapper.toDomain
import com.crypto.wallet.feature.transaction.presentation.mapper.toUiModel
import com.crypto.wallet.feature.transaction.presentation.model.CategoryUiModel
import com.crypto.wallet.ui.common.TextUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
  @IoDispatcher private val dispatcher: CoroutineDispatcher,
  private val isValidTransaction: IsValidTransaction,
  private val getCategories: GetCategories,
  private val addTransaction: AddTransaction,
  private val changeBalance: ChangeBalance,
  private val getUserBalance: GetUserBalance,
) : ViewModel() {
  private val mutableState = MutableStateFlow(AddTransactionState.loading)
  val state: StateFlow<AddTransactionState> = mutableState.asStateFlow()

  init {
    observeUserBalance()
    fetchCategories()
  }

  fun onEvent(event: AddTransactionEvent) {
    when (event) {
      is AddTransactionEvent.AddTransaction -> performOutcomeTransaction(event.amount)
      is AddTransactionEvent.ValidateInputValue -> validateInputValue(event.value)
      is AddTransactionEvent.ProcessCategoryClick -> processCategoryClick(event.category)
      AddTransactionEvent.DismissError -> dismissError()
    }
  }

  private fun observeUserBalance() {
    getUserBalance()
      .onEach { balanceResult ->
        balanceResult.fold(
          onSuccess = { balance ->
            mutableState.update { state ->
              state.copy(
                userBalance = balance.value,
              )
            }
          },
          onFailure = { error ->
            Log.e("AddTransactionViewModel", "observeUserBalance: ", error)
            mutableState.update {
              it.copy(errorMessage = TextUiModel(R.string.general_error_message))
            }
          }
        )
      }
      .launchIn(viewModelScope)
  }

  private fun fetchCategories() {
    mutableState.update { state ->
      state.copy(
        allCategories = getCategories().map { it.toUiModel() },
      )
    }
  }

  private fun performOutcomeTransaction(amount: Double) {
    val transactionCategory = state.value.selectedCategory?.toDomain() ?: return
    viewModelScope.launch(dispatcher) {
      changeBalance(
        transactionType = TransactionType.Outcome(transactionCategory),
        amount = amount,
      ).fold(
        onSuccess = {
          addTransaction(
            amount = amount,
            transactionCategory = transactionCategory,
          )
        },
        onFailure = { error ->
          Log.e("AddTransactionViewModel", "performOutcomeTransaction: ", error)
          mutableState.update {
            it.copy(errorMessage = TextUiModel(R.string.general_error_message))
          }
        }
      )
    }
  }

  private fun addTransaction(
    amount: Double,
    transactionCategory: OutcomeTransactionCategory,
  ) {
    val createTransactionInput = CreateTransactionInput(
      transactionType = TransactionType.Outcome(
        category = transactionCategory,
      ),
      creationDate = Instant.now(),
      amount = amount,
    )
    viewModelScope.launch(dispatcher) {
      addTransaction(createTransactionInput)
        .fold(
          onSuccess = {
            mutableState.update {
              it.copy(transactionAdded = Unit)
            }
          },
          onFailure = { error ->
            Log.e("AddTransactionViewModel", "observeUserBalance: ", error)
            mutableState.update {
              it.copy(errorMessage = TextUiModel(R.string.general_error_message))
            }
          },
        )
    }
  }

  private fun validateInputValue(input: String) {
    mutableState.update {
      it.copy(
        isValidInput = isValidTransaction(
          amount = input,
          userBalance = state.value.userBalance,
        ),
        inputValue = input,
      )
    }
  }

  private fun processCategoryClick(category: CategoryUiModel) {
    mutableState.update {
      it.copy(selectedCategory = category)
    }
  }

  private fun dismissError() {
    mutableState.update {
      it.copy(errorMessage = null)
    }
  }
}