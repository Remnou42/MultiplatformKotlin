package pssa.mobilelocker.locker

/**
 * The `Action` class is used to control the servo motor.
 *
 * The `Action` class provides functionality to control the angle of a servo motor
 *  by interfacing with GPIO pins. It allows setting and
 *  tracking the current angle of the servo.
 *
 * @param pin The GPIO pin number to which the servo motor is connected.
 * @property angleValue The current angle of the servo motor.
 */
class Action(pin: Int) {
    private val servoPin = pin
    var angleValue = 0
    private val gpio = Gpio()

    /**
     * Sets the angle of the servo motor connected to the specified GPIO pin.
     *
     * This function controls the servo by adjusting its angle
     * to the specified value. The angle value is updated to reflect the servo's current
     * position.
     *
     * @param angle The target angle to set for the servo motor,  value either 0 or  500-2500.
     */
    fun servoAngle(angle: Int) {
        gpio.servoControl(servoPin, angle)
        angleValue = angle
    }
}