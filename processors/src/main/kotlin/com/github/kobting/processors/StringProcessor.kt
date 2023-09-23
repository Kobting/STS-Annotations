package com.github.kobting.processors

import com.github.kobting.annotations.data.Language
import com.google.devtools.ksp.containingFile
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSValueArgument
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate

abstract class StringProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>
) : SymbolProcessor {

    /**
     * Name of file without file extension
     */
    abstract val fileName: String

    /**
     * Fully qualified name of the annotation.
     *
     * Example: Annotation::class.java.name
     */
    abstract val annotationName: String

    /**
     * Index of the language parameter in the annotation arguments
     */
    abstract val languageArgumentName: String

    abstract fun createFileContents(annotations: List<KSAnnotation>): String

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver
            .getSymbolsWithAnnotation(annotationName)
            .filterIsInstance<KSClassDeclaration>()

        if (!symbols.iterator().hasNext()) return emptyList()

        //For some reason when running while building cannot cast to Language to have to use hack findLanguage function
        //Casting to Language works once built into a JAR.
        val stringsMappedByLanguage = symbols.flatMap { it.annotations }.groupBy {
            try {
                (it.arguments.findArgument(languageArgumentName).value as Language).abbreviation
            } catch (ex: ClassCastException) {
                findLanguage(it.arguments.findArgument(languageArgumentName).value.toString())
            }
        }

        stringsMappedByLanguage.forEach { strings ->
            createStrings(
                strings.key,
                strings.value
            )
        }

        symbols.forEach { it.accept(KSVisitorVoid(), Unit) }

        return symbols.filterNot { it.validate() }.toList()
    }

    //This is needed because argument order is not guaranteed to match parameter order of the annotation class
    fun KSAnnotation.findArgumentIndex(argumentName: String): Int {
        return arguments.indexOfFirst { argumentName == it.name?.getShortName() }
    }

    //This is needed because argument order is not guaranteed to match parameter order of the annotation class
    fun List<KSValueArgument>.findArgument(argumentName: String): KSValueArgument {
        return this.find { argumentName == it.name!!.getShortName() } ?: error("No argument named $argumentName")
    }


    private fun createStrings(language: String, annotations: List<KSAnnotation>) {
        val resourceBasePath = options[OPTION_RESOURCE_BASE_PATH] ?: throw IllegalStateException("Missing ksp option for $OPTION_RESOURCE_BASE_PATH")

        val stringsFile = codeGenerator.createNewFile(
            //This line fixes string files getting deleted when nothing that would change them happens and there is a rebuild
            Dependencies(true, *annotations.mapNotNull { it.containingFile }.toTypedArray()),
            packageName = "${resourceBasePath}//$language",
            fileName = fileName,
            extensionName = "json"
        )

        val stringsJson = createFileContents(annotations)

        stringsFile.bufferedWriter().use { writer -> writer.write(stringsJson) }
    }

    /**
     * Hacky way to access [Language.abbreviation].
     * For some reason you cannot cast a [com.google.devtools.ksp.symbol.KSValueArgument.value]
     * to an enum that it is holding but [Object.toString] on [com.google.devtools.ksp.symbol.KSValueArgument.value]
     * will return the correct fully qualified class name.
     *
     *
     */
    private fun findLanguage(languageClassString: String): String {
        //Getting com.github.kobting.annotations.data.Language from (example) com.github.kobting.annotations.data.Language.ENGLISH
        val languageClazz = try {
            Class.forName(languageClassString.split(".").dropLast(1).joinToString(separator = "."))
        } catch (ex: Exception) {
            error("Class not found: $languageClassString")
        }

        languageClazz.enumConstants.forEach {
            if (it.toString() == languageClassString.split(".").last()) {
                /**
                 * Gets value for [Language.abbreviation]
                 * It is a declared method due to kotlin -> java code generation
                 */
                return it::class.java.getDeclaredMethod("getAbbreviation").invoke(it).toString()
            }
        }

        throw IllegalStateException("Should never happen. Matching on a language not in ${Language::class.java.name}")
    }

}