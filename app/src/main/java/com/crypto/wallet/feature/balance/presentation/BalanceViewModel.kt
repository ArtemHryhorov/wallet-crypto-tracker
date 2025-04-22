package com.crypto.wallet.feature.balance.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.wallet.R
import com.crypto.wallet.core.dispatchers.IoDispatcher
import com.crypto.wallet.feature.balance.domain.usecase.CreateUserBalance
import com.crypto.wallet.feature.balance.domain.usecase.GetUserBalance
import com.crypto.wallet.feature.balance.domain.usecase.IsValidTopUpAmount
import com.crypto.wallet.feature.balance.domain.usecase.TopUpBalance
import com.crypto.wallet.feature.balance.presentation.model.BalanceUiModel
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
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
  @IoDispatcher private val dispatcher: CoroutineDispatcher,
  private val getUserBalance: GetUserBalance,
  private val isValidTopUpAmount: IsValidTopUpAmount,
  private val topUpBalance: TopUpBalance,
  private val createUserBalance: CreateUserBalance,
) : ViewModel() {
  private val mutableState = MutableStateFlow(BalanceState.loading)
  val state: StateFlow<BalanceState> = mutableState.asStateFlow()

  init {
    fetchUserBalance()
  }

  fun onEvent(event: BalanceEvent) {
    when (event) {
      BalanceEvent.ShowTopUpDialog -> showTopUpDialog()
      BalanceEvent.DismissTopUpDialog -> dismissTopUpDialog()
      is BalanceEvent.TopUpBalance -> topUpUserBalance(event.amount)
      is BalanceEvent.ValidateTopUpValue -> validateTopUpValue(event.value)
    }
  }

  private fun fetchUserBalance() {
    getUserBalance()
      .onEach { balanceResult ->
        balanceResult.fold(
          onSuccess = { balance ->
            mutableState.update {
              it.copy(balance = BalanceUiModel.from(balance))
            }
          },
          onFailure = { error ->
            Log.e("BalanceViewModel", "fetchUserBalance: Failed to load", error)
            // TODO - Add NoInternetConnection handling
            when (error) {
              is NoSuchElementException -> createUserBalance()
              else -> mutableState.update {
                it.copy(errorMessage = TextUiModel(R.string.general_error_message))
              }
            }
          },
        )
      }
      .launchIn(viewModelScope)
  }

  private fun topUpUserBalance(amount: Double) {
    viewModelScope.launch(dispatcher) {
      topUpBalance(amount)
        .onSuccess {
          mutableState.update {
            it.copy(showTopUpDialog = false)
          }
        }
        .onFailure {
          mutableState.update {
            it.copy(
              errorMessage = TextUiModel(R.string.general_error_message),
              showTopUpDialog = false,
            )
          }
        }
    }
  }

  private fun showTopUpDialog() {
    mutableState.update {
      it.copy(showTopUpDialog = true)
    }
  }

  private fun dismissTopUpDialog() {
    mutableState.update {
      it.copy(showTopUpDialog = false)
    }
  }

  private fun validateTopUpValue(amount: String) {
    mutableState.update {
      it.copy(
        isTopUpDialogValueValid = isValidTopUpAmount(amount),
        topUpDialogValue = amount,
      )
    }
  }
}