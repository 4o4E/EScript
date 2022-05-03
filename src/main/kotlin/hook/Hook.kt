package top.e404.escript.hook

interface Hook {
    /**
     * 检测是否启用挂钩
     *
     * @return 若启用则返回true
     */
    fun enable(): Boolean

    /**
     * 更新挂钩
     *
     * @return 若启用则返回true
     */
    fun update(): Boolean
}