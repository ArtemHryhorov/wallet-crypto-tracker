package com.crypto.wallet.feature.balance.domain

import javax.inject.Inject

// TODO - Implement during balance top up feature

class GetUserBalanceUseCase @Inject constructor() : GetUserBalance {
  override fun invoke(): String = "2.53078 BTC"
}

fun interface GetUserBalance {
  operator fun invoke(): String
}