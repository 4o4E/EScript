package top.e404.escript.script.executable.list

import top.e404.escript.annotation.ExecutionSign
import top.e404.escript.script.executable.Execution
import top.e404.escript.script.executable.ExecutionParser

@ExecutionSign
object Kill : ExecutionParser {
    override val headRegex = Regex("(?i)kill")
    override fun parse(content: Any?) = Execution { it.health = 0.0 }
}