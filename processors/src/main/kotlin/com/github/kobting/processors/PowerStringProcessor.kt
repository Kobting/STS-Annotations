package com.github.kobting.processors

import com.github.kobting.annotations.PowerString
import com.github.kobting.annotations.data.FileName
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSAnnotation

class PowerStringProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>
): StringProcessor(codeGenerator, logger, options) {

    companion object {
        private val NAME_PREFIX = PowerString::class.java.declaredMethods[0].name
        private val NAME_NAME = PowerString::class.java.declaredMethods[1].name
        private val NAME_DESCRIPTIONS = PowerString::class.java.declaredMethods[2].name
    }

    override val fileName: String = FileName.POWER_STRINGS.fileName
    override val annotationName: String = PowerString::class.java.name
    override val languageArgumentName: String = "language"

    @Suppress("UNCHECKED_CAST")
    override fun createFileContents(annotations: List<KSAnnotation>): String {
        val powerStringsJson = annotations.joinToString(prefix = "{\n", postfix = "\n}", separator = ",\n") { annotation ->
            with(annotation.arguments) {
                """
                |    "${findArgument(NAME_PREFIX).value}:${findArgument(NAME_NAME).value}": {
                |        "NAME": "${findArgument(NAME_NAME).value}",
                |        "DESCRIPTIONS": [
                |            ${(findArgument(NAME_DESCRIPTIONS).value as ArrayList<String>).joinToString(separator = ",", transform = { "\"$it\"" }) }
                |        ]
                |    }
                |""".trimMargin().trimEnd()
            }
        }

        return powerStringsJson
    }


}