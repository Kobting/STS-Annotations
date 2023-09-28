package com.github.kobting.annotations

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class Card(
    val unlocked: Boolean = false
)

