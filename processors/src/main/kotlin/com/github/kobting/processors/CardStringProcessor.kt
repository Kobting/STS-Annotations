package com.github.kobting.processors

import com.github.kobting.annotations.CardString
import com.github.kobting.annotations.CardStrings
import com.github.kobting.annotations.data.FileName
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate

class CardStringProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>
): StringProcessor(codeGenerator, logger, options) {

    companion object {
        private const val INDEX_PREFIX = 0
        private const val INDEX_NAME = 1
        private const val INDEX_DESCRIPTION = 2
    }

    override val fileName: String = FileName.CARD_STRINGS.fileName
    override val annotationName: String = CardString::class.java.name
    override val languageArgumentIndex: Int = 3

    override fun createFileContents(annotations: List<KSAnnotation>): String {
        val cardStringJson = annotations.joinToString(prefix = "{\n", postfix = "\n}", separator = ",\n") { annotation ->
            with(annotation.arguments) {
                """
                |    "${this[INDEX_PREFIX].value}:${this[INDEX_NAME].value}": {
                |        "NAME": "${this[INDEX_NAME].value}",
                |        "DESCRIPTION": "${this[INDEX_DESCRIPTION].value}"
                |    }
                |""".trimMargin().trimEnd()
            }
        }

        return cardStringJson
    }

}