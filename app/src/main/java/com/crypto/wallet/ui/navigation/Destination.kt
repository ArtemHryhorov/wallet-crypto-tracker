package com.crypto.wallet.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
  @Serializable
  data object Balance : Destination

  @Serializable
  data object AddTransaction : Destination
}