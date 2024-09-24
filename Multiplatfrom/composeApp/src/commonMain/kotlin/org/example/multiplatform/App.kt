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

import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
@Preview
fun App() {
    var gpioOutput by remember { mutableStateOf("Press to get GPIO 27 state") }

    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            Button(onClick = { led17On() }) {
                Text("LED 17 ON")
            }

            Button(onClick = { led17Off() }) {
                Text("LED 17 OFF")
            }

            Button(onClick = { gpioOutput = get27() }) {
                Text("Get GPIO 27 State")
            }

            Text(gpioOutput)

        }
    }
}

fun led17On() {
    try {
        Runtime.getRuntime().exec("gpioset gpiochip0 17=1")
    } catch (e: Exception) {
        println(e)
    }
}

fun led17Off() {
    try {
        Runtime.getRuntime().exec("gpioset gpiochip0 17=0")
    } catch (e: Exception) {
        println(e)
    }
}

fun get27(): String {
    return try {
        val process = Runtime.getRuntime().exec("gpioget gpiochip0 27")
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = reader.readLine()
        reader.close()
        return output
    } catch (e: Exception) {
        println(e)
        "Error"
    }
}