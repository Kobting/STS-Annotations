package com.github.kobting.processors

import com.github.kobting.annotations.CardString
import com.github.kobting.annotations.data.FileName
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSValueArgument

class CardStringProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>
): StringProcessor(codeGenerator, logger, options) {

    companion object {
        private const val NAME_PREFIX = "prefix"
        private const val NAME_NAME = "name"
        private const val NAME_DESCRIPTION = "description"
        private const val NAME_UPGRADE_DESCRIPTION = "upgradeDescription"
        private const val NAME_EXTENDED_DESCRIPTIONS = "extendedDescription"
        private const val NAME_LANGUAGE = "language"
    }

    override val fileName: String = FileName.CARD_STRINGS.fileName
    override val annotationName: String = CardString::class.java.name
    override val languageArgumentName: String = NAME_LANGUAGE

    override fun createFileContents(annotations: List<KSAnnotation>): String {
        val cardStringJson = annotations.joinToString(prefix = "{\n", postfix = "\n}", separator = ",\n") { annotation ->
            with(annotation.arguments) {
                val extendedDescription = extendedDescriptionOrEmpty()
                val upgradeDescription = upgradeDescriptionOrEmpty(extendedDescription.isNotEmpty())
                val hasUpgradeOrExtendedDescriptions = upgradeDescription.isNotEmpty() || extendedDescription.isNotEmpty()
                """
                |    "${findArgument(NAME_PREFIX).value}:${findArgument(NAME_NAME).value}": {
                |        "NAME": "${findArgument(NAME_NAME).value}",
                |        "DESCRIPTION": "${findArgument(NAME_DESCRIPTION).value}"${if (hasUpgradeOrExtendedDescriptions) "," else ""}
                |        $upgradeDescription
                |        $extendedDescription
                |    }
                |""".trimMargin().trimEnd()
            }
        }

        return cardStringJson
    }

    private fun List<KSValueArgument>.upgradeDescriptionOrEmpty(addEndingComma: Boolean): String {
        val upgradeDescription = findArgument(NAME_UPGRADE_DESCRIPTION).value.toString()
        if (upgradeDescription.isEmpty()) {
            return ""
        } else {
            return """"UPGRADE_DESCRIPTION": "$upgradeDescription"${if (addEndingComma) "," else ""}"""
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun List<KSValueArgument>.extendedDescriptionOrEmpty(): String {
        val foundArgument = try { findArgument(NAME_EXTENDED_DESCRIPTIONS).value } catch (ex: Exception) { "" }
        val extendedDescription = if (foundArgument is String) {
            foundArgument
        } else {
            (foundArgument as ArrayList<String>).joinToString(separator = ",", transform = { "\"$it\"" })
        }
        if (extendedDescription.isEmpty()) {
            return ""
        } else {
            return """
            |"EXTENDED_DESCRIPTION": [
            |           $extendedDescription
            |       ]
            """.trimMargin()
        }
    }

}