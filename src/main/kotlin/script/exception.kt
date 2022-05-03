package top.e404.escript.script

/**
 * 没有找到对应的解析器
 *
 * @param message 详细信息
 */
class NoSuchParserException(message: String) : Exception(message)

/**
 * 编译时异常
 */
class ParseException : Throwable {
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(message: String) : super(message)
}

/**
 * 执行时异常
 */
class ExecutionException : Throwable {
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(message: String) : super(message)
}