package pssa.mobilelocker.locker

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.FileInputStream

/**
 * Gpio class to control GPIO pins
 *
 * The `Gpio` class uses system commands to execute GPIO-related actions, such as
 * controlling servo motors, LEDs, and reading GPIO pin values.
 */
class Gpio {

    /**
     * Sets the angle of a servo motor connected to a specific GPIO pin.
     *
     * Executes a system command to control the servo angle.
     *
     * @param pin The GPIO pin number to which the servo is connected.
     * @param angle The angle to set for the servo, in degrees.
     */
    fun servoControl(pin: Int, angle: Int) {
        Runtime.getRuntime().exec("pigs s $pin $angle")
    }

    /**
     * Controls a LED by setting the specified GPIO pin to a high or low state.
     *
     * Executes a system command to set the GPIO pin value.
     *
     * @param pin The GPIO pin number where the LED is connected.
     * @param value The desired state for the LED.
     */
    fun ledControl(pin: Int, value: Int) {
        Runtime.getRuntime().exec("gpioset gpiochip0 ${pin}=$value")
    }

    /**
     * Retrieves the current value of a specified GPIO pin.
     *
     * Executes a system command to read the GPIO pin value
     * and returns the result as a string.
     *
     * @param gpio The GPIO pin number to read.
     * @return The current value of the GPIO pin as a string.
     */
    fun getPinValue(gpio: Int): String {
        val process = Runtime.getRuntime().exec("gpioget gpiochip0 $gpio")
        return BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
            reader.readLine()
        }
    }

    /**
     * Scans and retrieves a barcode from the specified serial port.
     *
     * Reads data from the serial port, expecting a barcode string, and returns
     * the barcode or a message if no barcode is detected.
     *
     * @param port The path to the serial port from which to read the barcode.
     * @return The scanned barcode as a string, or a message if not detected.
     */
    fun scanBarcode(port: String): String {
        val inputStream = FileInputStream(port)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val barcode = reader.readLine()
        reader.close()

        return barcode ?: "No barcode detected or error occurred"
    }
}