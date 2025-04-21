package com.crypto.wallet.feature.balance.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.wallet.core.IoDispatcher
import com.crypto.wallet.feature.balance.domain.GetUserBalance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
  @IoDispatcher private val dispatcher: CoroutineDispatcher,
  private val getUserBalance: GetUserBalance,
) : ViewModel() {

  private val mutableState: MutableStateFlow<BalanceState> =
    MutableStateFlow(BalanceState.loading)

  val state: StateFlow<BalanceState> =
    mutableState.asStateFlow()

  init {
    viewModelScope.launch(dispatcher) {
      mutableState.update {
        it.copy(balance = getUserBalance())
      }
    }
  }
}