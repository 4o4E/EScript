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
 * 标记为判断编译器
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ConditionSign

@SupportedAnnotationTypes("top.e404.escript.annotation.ConditionSign")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ConditionProcessor : AbstractProcessor() {
    override fun process(
        annotations: Set<TypeElement>,
        roundEnv: RoundEnvironment,
    ): Boolean {
        val list = roundEnv.filterHasAnnotation(ConditionSign::class.java)
        if (list.isNotEmpty()) File("src/main/resources/condition").writeText(list.joinToString("\n"))
        return true
    }
}