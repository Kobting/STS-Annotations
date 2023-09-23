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
        private val NAME_PREFIX = CardString::class.java.declaredMethods[0].name
        private val NAME_NAME = CardString::class.java.declaredMethods[1].name
        private val NAME_DESCRIPTION = CardString::class.java.declaredMethods[2].name
        private val NAME_UPGRADE_DESCRIPTION = CardString::class.java.declaredMethods[3].name
        private val NAME_EXTENDED_DESCRIPTIONS = CardString::class.java.declaredMethods[4].name
        private val NAME_LANGUAGE = CardString::class.java.declaredMethods[5].name
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
        val extendedDescription = findArgument(NAME_EXTENDED_DESCRIPTIONS).value as ArrayList<String>
        if (extendedDescription.isEmpty()) {
            return ""
        } else {
            return """
            |"EXTENDED_DESCRIPTION": [
            |           ${extendedDescription.joinToString(separator = ",", transform = { "\"$it\"" })}
            |       ]
            """.trimMargin()
        }
    }

}