package com.stock.api.model.enums

import javax.persistence.AttributeConverter

class DeviceTypeConverter : AttributeConverter<DeviceType?, String?> {
    override fun convertToDatabaseColumn(deviceType: DeviceType?): String? {
        return deviceType?.toString()
    }

    override fun convertToEntityAttribute(value: String?): DeviceType? {
        return if (value == null) {
            null
        } else DeviceType.toEnum(value)
    }
}