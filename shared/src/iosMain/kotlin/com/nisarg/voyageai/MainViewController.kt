package com.nisarg.voyageai

import androidx.compose.ui.window.ComposeUIViewController
import com.nisarg.voyageai.di.startSharedKoin

fun MainViewController() = ComposeUIViewController {
    startSharedKoin()
    App()
}