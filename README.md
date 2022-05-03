# 脚本结构

一个完整的脚本由以下结构构成

1. 判断模式(yml文件中的`condition_any`)
2. 判断列表(yml文件中的`condition`)
3. 条件满足时的执行列表(yml文件中的`on_allow`)
4. 条件不满足时的执行列表(yml文件中的`on_deny`)

## 判断模式

若设置为true则条件中任意一条满足即判断为满足条件, 可不写, 不写默认为false(需要所有条件都满足)

## 判断列表

一个列表, 组成列表的是[判断项](docs/condition.md)

## 执行列表

一个列表, 自称列表的是[执行项](docs/execution.md)