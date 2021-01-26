package com.stock.api.model.enums

enum class DeviceType(private val device: Device, private val value: String) {
    A(Device.MOBILE, "Android"), I(Device.MOBILE, "iOS"), P(Device.PC, "PC");

    fun isDevice(device: Device?): Boolean? {
        return device?.equals(device)
    }

    companion object {
        fun toEnum(value: String): DeviceType {
            for (deviceType in values()) {
                if (value == deviceType.value) {
                    return deviceType
                }
            }
            throw IllegalArgumentException("No Enum specified for this device type value")
        }

        fun toEnum(device: Device): Set<DeviceType> {
            return when {
                Device.MOBILE === device -> {
                    setOf(A, I)
                }
                Device.PC === device -> {
                    setOf(P)
                }
                else -> {
                    throw IllegalArgumentException("No Enum specified for this device type value")
                }
            }
        }
    }

}