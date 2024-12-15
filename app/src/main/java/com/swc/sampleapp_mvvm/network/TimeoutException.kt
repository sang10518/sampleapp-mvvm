package com.swc.sampleapp_mvvm.network

// Custom Exception Class for Timeouts
class TimeoutException(message: String, val timeoutType: TimeoutType) : Exception(message)

// Enum class for Timeout Types (optional, but useful for distinguishing different timeouts)
enum class TimeoutType {
    CONNECTION_TIMEOUT,
    READ_TIMEOUT,
    WRITE_TIMEOUT
}
