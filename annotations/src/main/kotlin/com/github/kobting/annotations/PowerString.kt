package com.github.kobting.annotations

import com.github.kobting.annotations.data.Language

@JvmRepeatable(PowerStrings::class)
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class PowerString(
    val prefix: String,
    val name: String,
    val descriptions: Array<String>,
    val language: Language
)

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class PowerStrings(val value: Array<PowerString>)