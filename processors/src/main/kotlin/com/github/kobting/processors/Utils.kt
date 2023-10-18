package com.github.kobting.processors

import com.google.devtools.ksp.symbol.KSValueArgument

//This is needed because argument order is not guaranteed to match parameter order of the annotation class
fun List<KSValueArgument>.findArgument(argumentName: String): KSValueArgument {
    return this.find { argumentName == it.name!!.getShortName() } ?: error("No argument named $argumentName for ${this.joinToString { it.name?.asString() ?: "null" }}")
}