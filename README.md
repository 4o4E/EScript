<div align="center">

# EScript

> 基于BukkitAPI的脚本插件, 适用于Spigot或Paper以及其他绝大多数Bukkit的下游分支核心

[![Release](https://img.shields.io/github/v/release/4o4E/EScript?label=Release)](https://github.com/4o4E/EScript/releases/latest)
[![Downloads](https://img.shields.io/github/downloads/4o4E/EScript/total?label=Download)](https://github.com/4o4E/EScript/releases)

</div>

## 脚本结构

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

## 指令

> 插件主命令为`escript`，包括缩写`esc`、`es`，如果与其他插件指令冲突，请使用`escript`

1. `escript reload` 重载
2. `escript exec <脚本名> <玩家id>` 以玩家为目标执行脚本
3. `escript cd info <cd名字> <玩家名字>` 查看此玩家的自定义cd信息
4. `escript cd set <cd名字> <玩家名字> <时长>` 设置此玩家的自定义cd
5. `escript cd add <cd名字> <玩家名字> <时长>` 为此玩家增加自定义cd时长
6. `escript cd minus <cd名字> <玩家名字> <时长>` 为此玩家减少自定义cd时长
7. `escript cd reset <cd名字> <玩家名字>` 为此玩家增加自定义cd时长

## 权限

- `escript.admin` 允许使用插件指令

## 配置

[config.yml](src/main/resources/config.yml)

## 脚本示例

[example.yml](src/main/resources/example.yml)

## 下载

- [最新版](https://github.com/4o4E/EScript/releases/latest)

## 更新记录

```
2022.05.04 - 1.0.0 发布插件
```

## bstats

![bstats](https://bstats.org/signatures/bukkit/EScript.svg)