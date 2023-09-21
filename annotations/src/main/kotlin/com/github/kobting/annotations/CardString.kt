package com.github.kobting.annotations

import com.github.kobting.annotations.data.Language

@JvmRepeatable(CardStrings::class)
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class CardString(
    val prefix: String,
    val name: String,
    val description: String,
    val language: Language
)

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class CardStrings(val value: Array<CardString>)
