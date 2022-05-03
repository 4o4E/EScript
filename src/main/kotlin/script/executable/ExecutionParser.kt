package top.e404.escript.script.executable

interface ExecutionParser {
    val headRegex: Regex
    fun parse(content: Any?): Execution
    fun matchHead(head: String) = headRegex.matches(head)
}