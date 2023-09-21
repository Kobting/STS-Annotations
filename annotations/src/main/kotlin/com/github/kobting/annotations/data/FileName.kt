package com.github.kobting.annotations.data

enum class FileName(val fileName: String) {
    CARD_STRINGS("card-strings"),
    POWER_STRINGS("power-strings"),
    RELIC_STRINGS("relic-strings");

    companion object {

        fun valueOfOrError(fileName: String): FileName {
            return when(fileName) {
                CARD_STRINGS.fileName -> CARD_STRINGS
                POWER_STRINGS.fileName -> POWER_STRINGS
                RELIC_STRINGS.fileName -> RELIC_STRINGS
                else -> throw IllegalArgumentException("$fileName is not a valid ${FileName::class.java.name} file name.")
            }
        }

    }
}