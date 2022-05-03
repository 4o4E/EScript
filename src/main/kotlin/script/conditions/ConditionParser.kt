package top.e404.escript.script.conditions

/**
 * ## 代表一个条件编译器
 *
 * 脚本格式: `hasPerm: minecraft.command.op`
 */
interface ConditionParser {
    /**
     * 匹配头数据的正则
     */
    val headRegex: Regex

    /**
     * 编译脚本, 将字符串处理成lambda
     *
     * @param content 条件
     * @return 根据玩家做出判断的lambda
     */
    fun parse(content: Any?): Condition

    /**
     * 检查脚本头是否匹配
     *
     * @param head 脚本头
     * @return 若匹配则返回true
     */
    fun matchHead(head: String) = headRegex.matches(head)
}