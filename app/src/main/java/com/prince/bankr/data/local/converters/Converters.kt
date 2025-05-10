package com.prince.bankr.data.local.converters

import androidx.room.TypeConverter
import com.prince.bankr.data.local.enums.Type
import java.util.Date

class Converters {

    // Date to Long and vice versa
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // Enum to String and vice versa
    @TypeConverter
    fun fromTransactionType(value: Type): String {
        return value.name
    }

    @TypeConverter
    fun toTransactionType(value: String): Type {
        return Type.valueOf(value)
    }
}