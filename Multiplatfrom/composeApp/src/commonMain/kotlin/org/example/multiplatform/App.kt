package org.example.multiplatform

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import multiplatfrom.composeapp.generated.resources.Res
import multiplatfrom.composeapp.generated.resources.compose_multiplatform
import java.io.DataOutputStream

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            Button(onClick = { led17On() }) {
                Text("LED 17 ON")
            }
            Button(onClick = { led17Off() }) {
                Text("LED 17 OFF")
            }


        }
    }
}

fun led17On() {
    Runtime.getRuntime().exec("gpioset gpiochip0 17=1")
}

fun led17Off() {
    Runtime.getRuntime().exec("gpioset gpiochip0 17=0")
}
