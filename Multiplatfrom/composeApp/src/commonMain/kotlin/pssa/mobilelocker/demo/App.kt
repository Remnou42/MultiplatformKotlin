@file:Suppress("SpellCheckingInspection")

package pssa.mobilelocker.demo

import pssa.mobilelocker.locker.Light
import pssa.mobilelocker.locker.Scan
import pssa.mobilelocker.locker.Action
import pssa.mobilelocker.locker.Detection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import pssa.mobilelocker.locker.ConfigLoader


import java.io.FileOutputStream


@Composable
@Preview
fun App() {
    //val configloader = ConfigLoader()
    //val config = configloader.loadConfig("config.json")

    var gpioOutput by remember { mutableStateOf("Press to get GPIO 27 state") }
    val scan =  Scan("/dev/ttyS0")
    val light = Light(1)
    val action = Action(1)
    val detection = Detection(1)

    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                light.ledOn() }}) {
                Text("LED 17 ON")
            }

            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                light.ledOff() }}) {
                Text("LED 17 OFF")
            }

            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    gpioOutput = detection.getPinValue() }}) {
                Text("Get GPIO 27 State")
            }

            Text(gpioOutput)

            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                action.servoAngle(500) }}) {
                Text("Angle 0°")
            }

            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                action.servoAngle(1500) }}) {
                Text("Angle 90°")
            }

            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                action.servoAngle(2500) }}) {
                Text("Angle 180°")
            }

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        scan.handleBarcodeScan()
                    }
                },
                enabled = !scan.isScanning
            ) {
                Text("Scan Barcode")
            }

            Text(scan.barcodeOutput)

            Button(
                onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    sendDataToSerialPort("foo")
                }
            }) {
                Text("Send Data to Serial Port")
            }

        }
    }
}

/**
 * Sends data to the serial port `/dev/serial0`.
 *
 * Writes a given string to the serial port, allowing external devices to read the data.
 *
 * @param data The data to send to the serial port.
 */
fun sendDataToSerialPort(data: String) {
    val outputStream = FileOutputStream("/dev/serial0")
    outputStream.write(data.toByteArray())
    outputStream.flush()
    outputStream.close()
}