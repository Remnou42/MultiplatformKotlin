package pssa.mobilelocker.locker

/**
 * Represents a barcode scanning mechanism for a specified serial port.
 *
 * The `Scan` class provides functionality to initiate and handle barcode scans
 * from a serial port. It keeps track of
 * the scan status and the last scanned barcode.
 *
 * @param port The serial port name to which the barcode scanner is connected.
 * @property isScanning The current status of the barcode scan: true if scanning, false if idle.
 * @property barcodeOutput The last scanned barcode value.
 */
class Scan(port: String) {
    var isScanning: Boolean = false
    var barcodeOutput: String = ""
    private val portName = port
    private val gpio = Gpio()

    /**
     * Reads a barcode from the specified serial port.
     *
     * This function retrieves a barcode string from
     * the serial port.
     *
     * @return The scanned barcode as a string.
     */
    private fun readBarcode(): String {
        return gpio.scanBarcode(portName)
    }

    /**
     * Initiates a barcode scan and updates the scan status.
     *
     * This method sets `isScanning` to true while a scan is in progress,
     * retrieves the barcode, and then updates `barcodeOutput`
     * with the result. The scan status is reset after the scan completes.
     */
    fun handleBarcodeScan() {
        isScanning = true
        barcodeOutput = readBarcode()
        isScanning = false
    }
}