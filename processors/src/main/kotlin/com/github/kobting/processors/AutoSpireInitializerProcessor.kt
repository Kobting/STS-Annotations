package com.github.kobting.processors

import basemod.interfaces.EditCardsSubscriber
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.github.kobting.annotations.AutoSpireInitializer
import com.github.kobting.annotations.Card
import com.github.kobting.annotations.data.FileName
import com.github.kobting.annotations.data.Language
import com.google.devtools.ksp.getAllSuperTypes
import com.google.devtools.ksp.getConstructors
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.Settings.GameLanguage
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.localization.RelicStrings
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.addOriginatingKSFile
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import java.io.File

class AutoSpireInitializerProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>
) : SymbolProcessor {

    companion object {
        private const val FILE_NAME = "AutoInitializer"
        private const val CLASS_NAME = "AutoInitialize"
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver
            .getSymbolsWithAnnotation(AutoSpireInitializer::class.java.name)
            .filterIsInstance<KSClassDeclaration>()

        if (!symbols.iterator().hasNext()) return emptyList()
        if (symbols.count() > 1) throw IllegalStateException("Can only have one ${AutoSpireInitializer::class.java.name} per project.")
        if (symbols.first().annotations.find { it.shortName.getShortName() == SpireInitializer::class.java.simpleName } == null) throw IllegalStateException("Class annotated with ${AutoSpireInitializer::class.java.name} also needs to be annotated with ${SpireInitializer::class.java.name}")
        val target = symbols.first()

        val cardSymbols = resolver
            .getSymbolsWithAnnotation(Card::class.java.name)
            .filterIsInstance<KSClassDeclaration>()

        val resourceBasePath = options[OPTION_RESOURCE_BASE_PATH]

        val fileSpec = FileSpec.builder(target.packageName.asString(), FILE_NAME)

        val autoInitializerTypeSpec = TypeSpec.classBuilder(CLASS_NAME).addOriginatingKSFile(target.containingFile!!).addModifiers(KModifier.OPEN)

        processCardAnnotations(autoInitializerTypeSpec, cardSymbols)

        if (resourceBasePath != null) {
            val supportedLanguages = findSupportedLanguagesAndFileNames(resourceBasePath, codeGenerator.generatedFile.toList())

            if (supportedLanguages.find { when (it.fileName) { FileName.CARD_STRINGS, FileName.POWER_STRINGS, FileName.RELIC_STRINGS -> true } } != null) {
                autoInitializerTypeSpec.addSuperinterface(basemod.interfaces.EditStringsSubscriber::class)

                val localizationPathFunction = FunSpec.builder("createLocalizationPath").addModifiers(KModifier.PRIVATE)
                localizationPathFunction.addParameter("language", GameLanguage::class.java)
                localizationPathFunction.addParameter("fileName", String::class)
                localizationPathFunction.returns(String::class)

                localizationPathFunction.addCode(CodeBlock.of(
                    """
                    |    val languagePath = when (language) {
                             ${supportedLanguages.map { it.language }.distinct().map { """|        ${it.toGameLanguage()} -> "$resourceBasePath/${it.abbreviation}/"""" }.joinToString(separator = "\n") }
                    |        else -> "${resourceBasePath}/eng/"
                    |    }
                    |    return languagePath + fileName + ".json"
                    |""".trimMargin()
                ))

                autoInitializerTypeSpec.addFunction(localizationPathFunction.build())

                val loadStringsFilesFunction = FunSpec.builder("loadStringsFiles").addModifiers(KModifier.PRIVATE)
                loadStringsFilesFunction.addParameter("language", GameLanguage::class.java)
                loadStringsFilesFunction.addCode(CodeBlock.of(supportedLanguages.createLoadCustomStringStatements("createLocalizationPath(language")))

                autoInitializerTypeSpec.addFunction(loadStringsFilesFunction.build())


                val receiveEditStringsFunction = FunSpec.builder("receiveEditStrings").addModifiers(KModifier.OVERRIDE)
                receiveEditStringsFunction.addCode(CodeBlock.of(
                    """
                        |loadStringsFiles(Settings.GameLanguage.ENG)
                        |if (Settings.language != Settings.GameLanguage.ENG) {
                        |   loadStringsFiles(Settings.language)
                        |}
                    """.trimMargin()
                ))

                autoInitializerTypeSpec.addFunction(receiveEditStringsFunction.build())
                fileSpec.addType(autoInitializerTypeSpec.build())
            }

        }

        try {
            fileSpec.build().writeTo(codeGenerator, Dependencies(true, *(listOf(target.containingFile!!) + cardSymbols.map { it.containingFile }.toList()).filterNotNull().toTypedArray()))
        } catch (ex: FileAlreadyExistsException) {
            /* Ignoring Multi-pass */
        }

        symbols.forEach { it.accept(KSVisitorVoid(), Unit) }

        return symbols.filterNot { it.validate() }.toList()
    }

    private data class LanguageAndFileName(val language: Language, val fileName: FileName)

    private fun List<LanguageAndFileName>.createLoadCustomStringStatements(localizationFuncCallStatement: String): String {
        val statements = this.map { it.fileName }.distinct().map {
            val clazz = when (it) {
                FileName.CARD_STRINGS -> CardStrings::class.java.name
                FileName.POWER_STRINGS -> PowerStrings::class.java.name
                FileName.RELIC_STRINGS -> RelicStrings::class.java.name
            }
            """basemod.BaseMod.loadCustomStringsFile(${clazz}::class.java, $localizationFuncCallStatement, "${it.fileName}"))"""
        }
        return statements.joinToString(separator = "\n")
    }

    private fun Language.toGameLanguage(): String {
        return when (this) {
            Language.ENGLISH -> "Settings.GameLanguage.ENG"
            Language.DUTCH -> "Settings.GameLanguage.DUT"
            Language.ESPERANTO -> "Settings.GameLanguage.EPO"
            Language.PORTUGUESE -> "Settings.GameLanguage.PTB"
            Language.SIMPLIFIED_CHINESE -> "Settings.GameLanguage.ZHS"
            Language.TRADITIONAL_CHINESE -> "Settings.GameLanguage.ZHT"
            Language.FINNISH -> "Settings.GameLanguage.FIN"
            Language.FRENCH -> "Settings.GameLanguage.FRA"
            Language.GERMAN -> "Settings.GameLanguage.DEU"
            Language.INDONESIAN -> "Settings.GameLanguage.IND"
            Language.ITALIAN -> "Settings.GameLanguage.ITA"
            Language.JAPANESE -> "Settings.GameLanguage.JPN"
            Language.KOREAN -> "Settings.GameLanguage.KOR"
            Language.NORWEGIAN -> "Settings.GameLanguage.NOR"
            Language.POLISH -> "Settings.GameLanguage.POL"
            Language.RUSSIAN -> "Settings.GameLanguage.RUS"
            Language.SPANISH -> "Settings.GameLanguage.SPA"
            Language.SERBIAN -> "Settings.GameLanguage.SRP"
            Language.SERBIAN_CYRILLIC -> "Settings.GameLanguage.SRB"
            Language.THAI -> "Settings.GameLanguage.THA"
            Language.TURKISH -> "Settings.GameLanguage.TUR"
            Language.UKRAINIAN -> "Settings.GameLanguage.UKR"
            Language.VIETNAMESE -> "Settings.GameLanguage.VIE"
        }
    }

    private fun findSupportedLanguagesAndFileNames(resourceBasePath: String, generatedFiles: List<File>): List<LanguageAndFileName> {
        val supportedLanguages = generatedFiles.asSequence().filter {
            try {
                Language.valueOfOrError(it.parentFile.name)
                FileName.valueOfOrError(it.name.substringBeforeLast("."))
                true //TODO: Compare with resourceBasePath
            } catch (ex: Exception) {
                false
            }
        }.map { LanguageAndFileName(Language.valueOfOrError(it.parentFile.name), FileName.valueOfOrError(it.name.substringBeforeLast("."))) }.distinct().toList()

        return supportedLanguages
    }

    private fun processCardAnnotations(autoInitializerSpec: TypeSpec.Builder, cardSymbols: Sequence<KSClassDeclaration>) {
        autoInitializerSpec.addSuperinterface(EditCardsSubscriber::class)

        val methodReceiveEditCards = FunSpec.builder("receiveEditCards").addModifiers(KModifier.OVERRIDE)

        cardSymbols.forEach {
            it.getAllSuperTypes().find { superType -> superType.declaration.qualifiedName?.asString() == AbstractCard::class.java.name } ?: error("${it.simpleName.asString()} must have have ${AbstractCard::class.java.name} in its parent types.")
            it.getConstructors().find { constructor -> constructor.parameters.isEmpty() } ?: error("${it.simpleName.asString()} must have a no args constructor to use ${Card::class.java.name}")
            methodReceiveEditCards.addStatement("basemod.BaseMod.addCard(${it.toClassName()}())")
        }

        autoInitializerSpec.addFunction(methodReceiveEditCards.build())
    }

}