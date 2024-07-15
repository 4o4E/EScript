@file:Suppress("UNUSED")

package top.e404.escript.annotation

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration

/**
 * 标记为执行编译器
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ExecutionSign


class ExecutionProcessor(environment: SymbolProcessorEnvironment) : SymbolProcessor {
    private val codeGenerator = environment.codeGenerator
    private val logger = environment.logger
    private val ann = ExecutionSign::class.java

    override fun process(resolver: Resolver): List<KSAnnotated> {
        // 若文件已存在则直接return
        val stream = try {
            codeGenerator.createNewFile(
                dependencies = Dependencies(false),
                packageName = "top.e404.escript.script.executions",
                fileName = "executions",
                extensionName = "kt"
            )
        } catch (e: Exception) {
            logger.warn("skip exists file top/e404/escript/script/executions/executions.kt")
            return emptyList()
        }
        val names = resolver.getSymbolsWithAnnotation(ann.name)
            .filterIsInstance<KSClassDeclaration>()
            .map { it.qualifiedName!!.asString() }
            .toSet()

        logger.warn("process ${names.size} conditions")
        stream.bufferedWriter().use { bw ->
            bw.appendLine("package top.e404.escript.script.executions").appendLine()

            // groupCommands
            bw.appendLine("// size: ${names.count()}")
            bw.appendLine("val allExecutions: Set<top.e404.escript.script.executable.ExecutionParser> = setOf(")
            names.forEach { bw.append("    ").append(it).appendLine(",") }
            bw.appendLine(")")
        }
        return emptyList()
    }
}

class ExecutionProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment) = ExecutionProcessor(environment)
}
