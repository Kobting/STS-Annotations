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
        private const val INDEX_PREFIX = 0
        private const val INDEX_NAME = 1
        private const val INDEX_FLAVOR = 2
        private const val INDEX_DESCRIPTIONS = 3
    }

    override val fileName: String = FileName.RELIC_STRINGS.fileName
    override val annotationName: String = RelicString::class.java.name
    override val languageArgumentIndex: Int = 4

    @Suppress("UNCHECKED_CAST")
    override fun createFileContents(annotations: List<KSAnnotation>): String {
        val relicStringJson = annotations.joinToString(prefix = "{\n", postfix = "\n}", separator = ",\n") { annotation ->
            with(annotation.arguments) {
                """
                |    "${this[INDEX_PREFIX].value}:${this[INDEX_NAME].value}": {
                |        "NAME": "${this[INDEX_NAME].value}",
                |        "FLAVOR": "${this[INDEX_FLAVOR].value}",
                |        "DESCRIPTIONS": [ 
                |            ${(this[INDEX_DESCRIPTIONS].value as ArrayList<String>).joinToString(separator = ",", transform = { "\"$it\"" })}
                |        ]
                |    }
                |""".trimMargin().trimEnd()
            }
        }

        return relicStringJson
    }


}