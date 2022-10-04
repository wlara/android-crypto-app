package io.github.wlara.cryptoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import io.github.wlara.cryptoapp.ui.HomeScreen
import io.github.wlara.cryptoapp.ui.theme.CryptoAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoAppTheme {
                HomeScreen()
            }
        }
    }
}