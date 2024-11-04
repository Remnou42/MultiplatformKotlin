package pssa.mobilelocker.locker

/**
 * Represents a detection mechanism for reading the value of a GPIO pin.
 *
 * The `Detection` class provides functionality to retrieve and store the current
 * value of a GPIO pin.
 *
 * @param gpio The GPIO pin number to read the value from.
 */
class Detection(gpio: Int) {
    private val gpioGet = gpio
    private var pinRetrievedValue = ""
    private val gpio = Gpio()

    /**
     * Retrieves the current value of the specified GPIO pin.
     *
     * This function reads the value of the GPIO pin and
     * updates `gpioValue` with the result. It returns the current pin value as a string.
     *
     * @return The current value of the GPIO pin as a string.
     */
    fun getPinValue(): String {
        pinRetrievedValue = gpio.getPinValue(gpioGet)
        return pinRetrievedValue
    }
}