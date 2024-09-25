package org.example.multiplatform

//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

//import multiplatfrom.composeapp.generated.resources.Res
//import multiplatfrom.composeapp.generated.resources.compose_multiplatform
//import java.io.DataOutputStream

import java.io.BufferedReader
import java.io.InputStreamReader

import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream


@Composable
@Preview
fun App() {
    var gpioOutput by remember { mutableStateOf("Press to get GPIO 27 state") }
    var barcodeOutput by remember { mutableStateOf("Scan a barcode") }

    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            Button(onClick = { led17(1) }) {
                Text("LED 17 ON")
            }

            Button(onClick = { led17(0) }) {
                Text("LED 17 OFF")
            }

            Button(onClick = { gpioOutput = get27() }) {
                Text("Get GPIO 27 State")
            }

            Text(gpioOutput)

            Button(onClick = { servoAngle(500) }) {
                Text("Angle 0°")
            }

            Button(onClick = { servoAngle(1500) }) {
                Text("Angle 90°")
            }

            Button(onClick = { servoAngle(2500) }) {
                Text("Angle 180°")
            }

            Button(onClick = { barcodeOutput = readFromBarcodeScanner() }) {
                Text("Scan Barcode")
            }

            Text(barcodeOutput)

        }
    }
}

fun led17(value: Int) {
    try {
        Runtime.getRuntime().exec("gpioset gpiochip0 17=$value")
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

fun servoAngle(angle: Int) {
    try {
        Runtime.getRuntime().exec("pigs s 18 $angle")
    } catch (e: Exception) {
        println(e)
    }
}

fun readFromBarcodeScanner(): String {
    val portName = "/dev/ttyAMA0" // Change this to your serial port

    return try {
        // Open the serial port
        val inputStream = FileInputStream(portName)
        val reader = BufferedReader(InputStreamReader(inputStream))

        // Attempt to read barcode data
        val barcode = reader.readLine() // Read a line from the serial port
        reader.close()

        // Return the barcode or a message if null or empty
        barcode ?: "No barcode detected or error occurred"
    } catch (e: IOException) {
        println(e)
        "Error reading barcode"
    }

}