package com.ks.finance.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromCurrency(currency: Currency): String = currency.name

    @TypeConverter
    fun toCurrency(currency: String): Currency = enumValueOf(currency)
}