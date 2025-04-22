package com.crypto.wallet.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.crypto.wallet.feature.balance.presentation.BalanceRoute
import com.crypto.wallet.ui.navigation.Destination
import com.crypto.wallet.ui.theme.Color
import com.crypto.wallet.ui.theme.CryptoWalletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)
    enableEdgeToEdge(
      statusBarStyle = SystemBarStyle.auto(
        lightScrim = Color.LightBackground.toArgb(),
        darkScrim = Color.DarkBackground.toArgb(),
      )
    )
    setContent {
      CryptoWalletTheme {
        Box(
          modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
        ) {
          val navController = rememberNavController()
          NavHost(
            navController = navController,
            startDestination = Destination.Balance,
            modifier = Modifier.fillMaxSize(),
          ) {
            // TODO - Navigate to Add Transaction
            composable<Destination.Balance> {
              BalanceRoute(
                onAddTransactionClick = {},
              )
            }
          }
        }
      }
    }
  }
}
