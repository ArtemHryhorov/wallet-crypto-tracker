package com.crypto.wallet.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
  background = Color.DarkBackground,
  surface = Color.DarkSurface,
)

private val LightColorScheme = lightColorScheme(
  background = Color.LightBackground,
  surface = Color.LightSurface,
)

@Composable
fun CryptoWalletTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
    typography = Typography,
    content = content
  )
}