package com.github.kobting.processors

import com.github.kobting.annotations.RelicString
import com.github.kobting.annotations.data.FileName
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSAnnotation

class RelicStringProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>
) : StringProcessor(codeGenerator, logger, options) {

    companion object {
        private val NAME_PREFIX = RelicString::class.java.declaredMethods[0].name
        private val NAME_NAME = RelicString::class.java.declaredMethods[1].name
        private val NAME_FLAVOR = RelicString::class.java.declaredMethods[2].name
        private val NAME_DESCRIPTIONS = RelicString::class.java.declaredMethods[3].name
    }

    override val fileName: String = FileName.RELIC_STRINGS.fileName
    override val annotationName: String = RelicString::class.java.name
    override val languageArgumentName: String = "language"

    @Suppress("UNCHECKED_CAST")
    override fun createFileContents(annotations: List<KSAnnotation>): String {
        val relicStringJson = annotations.joinToString(prefix = "{\n", postfix = "\n}", separator = ",\n") { annotation ->
            with(annotation.arguments) {
                """
                |    "${findArgument(NAME_PREFIX).value}:${findArgument(NAME_NAME).value}": {
                |        "NAME": "${findArgument(NAME_NAME).value}",
                |        "FLAVOR": "${findArgument(NAME_FLAVOR).value}",
                |        "DESCRIPTIONS": [ 
                |            ${(findArgument(NAME_DESCRIPTIONS).value as ArrayList<String>).joinToString(separator = ",", transform = { "\"$it\"" })}
                |        ]
                |    }
                |""".trimMargin().trimEnd()
            }
        }

        return relicStringJson
    }


}