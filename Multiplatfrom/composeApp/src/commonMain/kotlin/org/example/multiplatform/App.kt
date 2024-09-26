@file:Suppress("SpellCheckingInspection")

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
import kotlin.concurrent.thread


@Composable
@Preview
fun App() {
    // Créé les variables d'état pour les sorties GPIO et les codes-barres
    var gpioOutput by remember { mutableStateOf("Press to get GPIO 27 state") }
    var barcodeOutput by remember { mutableStateOf("Scan a barcode") }

    // Crée une colonne pour afficher les boutons et les textes
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

            Button(onClick = { barcodeOutput = prepareThread() }) {
                Text("Scan Barcode")
            }

            Text(barcodeOutput)

        }
    }
}

fun prepareThread(): String {
    var output = "No barcode detected or error occurred"
    thread {
        output = readFromBarcodeScanner()
    }
    return output
}

// Fonction pour allumer ou éteindre la LED 17
fun led17(value: Int) {
    try {
        Runtime.getRuntime().exec("gpioset gpiochip0 17=$value")
    } catch (e: Exception) {
        println(e)
    }
}

// Fonction pour obtenir l'état du GPIO 27. Execute la commande et lie la sortie
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

// Fonction pour définir l'angle du servo
fun servoAngle(angle: Int) {
    try {
        Runtime.getRuntime().exec("pigs s 18 $angle")
    } catch (e: Exception) {
        println(e)
    }
}

// Fonction pour lire un code-barres ou Qr code à partir du scanner
fun readFromBarcodeScanner(): String {
    // Fichier du port série
    val portName = "/dev/ttyAMA0"

    return try {
        // Ouvre un flux d'entrée pour lire les données du port série
        val inputStream = FileInputStream(portName)
        val reader = BufferedReader(InputStreamReader(inputStream))

        // Lit une ligne du port série
        val barcode = reader.readLine()
        reader.close()

        barcode ?: "No barcode detected or error occurred"
    } catch (e: IOException) {
        println(e)
        "Error reading barcode"
    }
}