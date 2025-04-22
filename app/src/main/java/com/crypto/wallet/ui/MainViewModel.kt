package com.crypto.wallet.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crypto.wallet.core.dispatchers.IoDispatcher
import com.crypto.wallet.feature.balance.domain.usecase.FetchBitcoinRate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  @IoDispatcher private val dispatcher: CoroutineDispatcher,
  private val fetchBitcoinRate: FetchBitcoinRate,
) : ViewModel() {

  fun fetchBitcoinRateRequest() {
    viewModelScope.launch(dispatcher) {
      fetchBitcoinRate()
    }
  }
}