@file:Suppress("UNUSED")

package top.e404.escript.annotation

import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

/**
 * 标记为执行编译器
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ExecutionSign

@SupportedAnnotationTypes("top.e404.escript.annotation.ExecutionSign")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ExecutableProcessor : AbstractProcessor() {
    override fun process(
        annotations: Set<TypeElement>,
        roundEnv: RoundEnvironment,
    ): Boolean {
        val list = roundEnv.filterHasAnnotation(ExecutionSign::class.java)
        if (list.isNotEmpty()) File("src/main/resources/execution").writeText(list.joinToString("\n"))
        return true
    }
}