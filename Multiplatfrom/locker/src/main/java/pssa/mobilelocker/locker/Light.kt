package pssa.mobilelocker.locker


/**
 * Represents a LED light control mechanism for a specified GPIO pin.
 *
 * The `Light` class provides methods to turn an LED on or off by controlling
 * a GPIO pin. It keeps track of the LED's current state.
 *
 * @param gpio The GPIO pin number to which the LED is connected.
 * @property ledValue The current state of the LED.
 */
class Light(gpio: Int) {
    private val ledPin = gpio
    var ledValue = 0
    private val gpio = Gpio()

    /**
     * Sets the LED to a specified state (ON or OFF).
     *
     * This private function controls the LED by setting
     * the GPIO pin to the provided value.
     *
     * @param value The desired LED state: 1 for ON, 0 for OFF.
     */
    private fun ledAction(value: Int) {
        gpio.ledControl(ledPin, value)
        ledValue = value
    }

    /**
     * Turns the LED on by setting the GPIO pin to high.
     *
     * This method turns ON the LED and updates `ledValue` to 1.
     */
    fun ledOn() {
        ledAction(1)
        ledValue = 1
    }

    /**
     * Turns the LED off by setting the GPIO pin to low.
     *
     * This method turns OFF the LED and updates `ledValue` to 0.
     */
    fun ledOff() {
        ledAction(0)
        ledValue = 0
    }
}