package com.github.kobting.annotations

import com.github.kobting.annotations.data.Language

@JvmRepeatable(RelicStrings::class)
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class RelicString(
    val prefix: String,
    val name: String,
    val flavor: String,
    val descriptions: Array<String>,
    val language: Language
)

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class RelicStrings(val value: Array<RelicString>)