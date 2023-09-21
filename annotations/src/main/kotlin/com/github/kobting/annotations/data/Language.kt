package com.github.kobting.annotations.data

enum class Language(val abbreviation: String) {
    ENGLISH("eng"),
    DUTCH("dut"),
    ESPERANTO("epo"),
    PORTUGUESE("ptb"),
    SIMPLIFIED_CHINESE("zhs"),
    TRADITIONAL_CHINESE("zht"),
    FINNISH("fin"),
    FRENCH("fra"),
    GERMAN("deu"),
    INDONESIAN("ind"),
    ITALIAN("ita"),
    JAPANESE("jpn"),
    KOREAN("kor"),
    NORWEGIAN("nor"),
    POLISH("pol"),
    RUSSIAN("rus"),
    SPANISH("spa"),
    SERBIAN("srp"),
    SERBIAN_CYRILLIC("srb"),
    THAI("tha"),
    TURKISH("tur"),
    UKRAINIAN("ukr"),
    VIETNAMESE("vie");

    companion object {

        fun valueOfOrError(abbreviation: String): Language {
            return when (abbreviation) {
                ENGLISH.abbreviation -> ENGLISH
                DUTCH.abbreviation -> DUTCH
                ESPERANTO.abbreviation -> ESPERANTO
                PORTUGUESE.abbreviation -> PORTUGUESE
                SIMPLIFIED_CHINESE.abbreviation -> SIMPLIFIED_CHINESE
                TRADITIONAL_CHINESE.abbreviation -> TRADITIONAL_CHINESE
                FINNISH.abbreviation -> FINNISH
                FRENCH.abbreviation -> FRENCH
                GERMAN.abbreviation -> GERMAN
                INDONESIAN.abbreviation -> INDONESIAN
                ITALIAN.abbreviation -> ITALIAN
                JAPANESE.abbreviation -> JAPANESE
                KOREAN.abbreviation -> KOREAN
                NORWEGIAN.abbreviation -> NORWEGIAN
                POLISH.abbreviation -> POLISH
                RUSSIAN.abbreviation -> RUSSIAN
                SPANISH.abbreviation -> SPANISH
                SERBIAN.abbreviation -> SERBIAN
                SERBIAN_CYRILLIC.abbreviation -> SERBIAN_CYRILLIC
                THAI.abbreviation -> THAI
                TURKISH.abbreviation -> TURKISH
                UKRAINIAN.abbreviation -> UKRAINIAN
                VIETNAMESE.abbreviation -> VIETNAMESE
                else -> throw IllegalArgumentException("$abbreviation is not a valid ${Language::class.java.name} abbreviation.")
            }
        }

    }
}