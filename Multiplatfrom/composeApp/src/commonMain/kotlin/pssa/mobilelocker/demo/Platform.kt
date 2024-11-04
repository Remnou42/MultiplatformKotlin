package pssa.mobilelocker.demo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform