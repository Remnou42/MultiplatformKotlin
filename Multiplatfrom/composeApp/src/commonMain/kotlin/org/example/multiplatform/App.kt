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

import purejavacomm.CommPortIdentifier
import purejavacomm.SerialPort
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
    var barcode = ""
    try {
        val portId = CommPortIdentifier.getPortIdentifier("/dev/serial0") // Adjust the port name as needed
        val serialPort = portId.open("BarcodeScanner", 2000) as SerialPort
        serialPort.setSerialPortParams(
            9600,
            SerialPort.DATABITS_8,
            SerialPort.STOPBITS_1,
            SerialPort.PARITY_NONE
        )

        val inputStream: InputStream = serialPort.inputStream
        val buffer = ByteArray(1024)
        var len: Int
        while (inputStream.read(buffer).also { len = it } > -1) {
            barcode += String(buffer, 0, len)
        }

        inputStream.close()
        serialPort.close()
    } catch (e: Exception) {
        println(e)
        barcode = "Error reading barcode"
    }
    return barcode
}