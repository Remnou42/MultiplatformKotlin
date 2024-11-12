package pssa.mobilelocker.locker

import java.io.File
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

/**
 * ConfigLoader class to load configuration from a JSON file
 *
 * The `ConfigLoader` class is used to load configuration settings from a JSON file.
 * The configuration file contains the GPIO pin numbers and serial port name.
 */
class ConfigLoader {
    @Serializable
    data class Config(
        val servoPin: Int,
        val inputPin: Int,
        val ledPin: Int,
        val portName: String
    )

    /**
     * Loads the configuration settings from a JSON file.
     *
     * Reads the JSON file and deserializes the content into a `Config` object.
     *
     * @param filePath The path to the JSON configuration file.
     * @return The `Config` object containing the configuration settings.
     */
    fun loadConfig(filePath: String): Config {
        val jsonContent = File(filePath).readText()
        return Json.decodeFromString(Config.serializer(), jsonContent)
    }
}