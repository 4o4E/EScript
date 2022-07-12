# 执行

## `console`

### 效果

以控制台身份执行指令

### 示例

```yaml
on_allow:
  - console: tell %player_name% 你好
```

## `command`

### 别名

- `cmd`

### 效果

使玩家执行指令

### 示例

```yaml
on_allow:
  - cmd: home
```

## `op`

### 效果

使玩家以op省份执行执行指令

不建议使用, 执行流程是设置玩家为op -> 执行指令 -> 重置玩家op

(若执行前不是op, 执行后也不会是op)

### 示例

```yaml
on_allow:
  - op: home
```

## `cooldown`

### 别名

- `cd`

### 效果

修改玩家的自定义cd

### 示例

```yaml
on_allow:
  - cooldown:
    # 处理类型
    # add: 添加cd
    # set: 设置cd
    # reset: 重置cd
    type: add
    # cd名字
    name: 自定义cd
    # cd数值
    # 单位毫秒
    value: 1000
```

## `delay`

### 别名

- `later`

### 效果

延迟执行此项后的执行项, 单位: 刻

### 示例

```yaml
on_allow:
  - delay: 20
```

## `freezing`

### 别名

- `freeze`

### 效果

设置玩家冰冻的刻度

### 示例

```yaml
on_allow:
  # 在原有霜冻刻度上+5
  - freeze: +5
  # 在原有霜冻刻度上-5
  - freeze: -5
  # 设置为5
  - freeze: 5
```

## `damage`

### 效果

对玩家造成伤害

### 示例

```yaml
on_allow:
  - damage: 20.0
```

## `health`

### 效果

修改玩家生命值

### 示例

```yaml
on_allow:
  # 在原有生命值基础上+10
  - health: +10
  # 在原有生命值基础上-10
  - health: -10
  # 设置生命值为10
  - health: 10
```

## `giveItem`

### 效果

给予玩家物品

### 示例

```yaml
on_allow:
  # 格式1
  - giveItem:
    type: golden apple
    name: 物品名字
    amount: 1
    lore:
      - awa
    enchant:
      unbreaking: 1
    # 若背包无空位则掉落
    drop_on_full: true
  # 格式2
  # 给予1个, 不修改其他内容
  - giveItem: golden apple
```

## `takeItem`

### 效果

收取玩家物品

### 示例

```yaml
on_allow:
  # 格式1
  - takeItem:
    type: golden apple
    name: 物品名字
    amount: 1
    lore:
      - awa
    enchant:
      unbreaking: 1
    # 数量不足也拿取
    take_not_enough: false
  # 格式2
  # 拿取1个, 不检测其他内容
  - takeItem: golden apple
```

## `level`

### 别名

- lvl
- exp
- xp

### 效果

修改玩家等级

### 示例

```yaml
on_allow:
  # 等级加一级
  - lv: 1l
  # 等级减一级
  - lv: -1l
  # 等级加1点
  - lv: 1
  # 等级减1点
  - lv: -1
```

## `maxHealth`

### 效果

设置最大生命值

### 示例

```yaml
on_allow:
  # 最大生命值在原有最大生命值的基础上+1
  - maxHealth: +1
  # 最大生命值在原有最大生命值的基础上-1
  - maxHealth: -1
  # 设置最大生命值
  - maxHealth: 20
```

## `message`

### 别名

- `msg`

### 效果

发送消息

### 示例

```yaml
on_allow:
  - msg: 你是%player_name%
```

## `giveMoney`

### 别名

- `giveBal`
- `giveBalance`

### 效果

给予金钱 **需要经济插件**

### 示例

```yaml
on_allow:
  - giveBal: 10.2
```

## `takeMoney`

### 别名

- `takeBal`
- `takeBalance`

### 效果

给予金钱 **需要经济插件**

### 示例

```yaml
on_allow:
  - takeBal: 10.2
```

## `addPotion`

### 别名

- `addEffect`

### 效果

添加药水效果

### 示例

```yaml
on_allow:
  # 格式1
  # 详细设置
  - addPotion:
      # 药水类型
      type: SLOW
      # 药水效果等级
      level: 1
      # 持续时长 不写默认20
      duration: 20
  # 格式2
  - addPotion: SLOW
```

## `delPotion`

### 别名

- `delEffect`

### 别名

- `delPotion`
- `rmPotion`
- `removePotion`

### 效果

移除药水效果(若存在)

### 示例

```yaml
on_allow:
  - rmPotion: SLOW
```

## `saturation`

### 效果

设置玩家饱和度(隐藏饱食度)

### 示例

```yaml
on_allow:
  # 在玩家原有饱和度的基础上增加饱和度
  - saturation: +10
  # 在玩家原有饱和度的基础上减少饱和度
  - saturation: -10
  # 设置玩家饱和度
  - saturation: 10
```

## `script`

### 效果

执行另一个脚本

### 示例

```yaml
on_allow:
  - script: script_example
```

## `vector`

### 效果

设置玩家向量

### 示例

```yaml
on_allow:
  - vector:
      # 在原有向量的基础上增加
      x: +3
      # 在原有向量的基础上减小
      y: -3
      # 设置向量
      z: 10
```

## `sound`

### 效果

在玩家位置播放声音, 所有周围的玩家均可听见

### 示例

```yaml
on_allow:
  # 格式1
  # 详细设置
  - sound:
      type: block.anvil.place
      volume: 1
      pitch: 1
  # 格式2
  # volume和pitch默认为1
  - sound: block.anvil.place
```

## `soundPlayer`

### 别名

- `sound`

### 效果

单独给玩家播放声音, 周围的其他玩家不会听见

### 示例

```yaml
on_allow:
  # 格式1
  # 详细设置
  - soundPlayer:
      type: block.anvil.place
      volume: 1
      pitch: 1
  # 格式2
  # volume和pitch默认为1
  - soundPlayer: block.anvil.place
```

## `particle`

### 效果

生成粒子

### 示例

```yaml
on_allow:
  # 格式1
  - particle:
      type: REDSTONE
      x: %math_{player_x}+1%
      y: %math_{player_y}+1%
      z: %math_{player_z}+1%
      count: 10
      offset_x: 1
      offset_y: 1
      offset_z: 1
      extra: 1.0
      # 粒子为REDSTONE时的选项
      data:
        color: #ffcccc
        size: 1.0
      # 粒子为ITEM_CRACK, BLOCK_CRACK, BLOCK_DUST, FALLING_DUST时的选项: 写物品类型
      # data: STONE
  # 格式2
  - particle: END_ROD
```

## `teleport`

### 别名

- tp

### 效果

将玩家传送至指定位置

### 示例

```yaml
on_allow:
  - tp:
      world: world
      x: 0.0
      y: 70.0
      z: 0.0
      yaw: 0.0 # 可省略, 默认值0
      pitch: 0.0 # 可省略, 默认值0
```

## `title`

### 效果

发送title

### 示例

```yaml
on_allow:
  # 格式1
  - title:
      title: title
      subtitle: subtitle
      # 淡入, 单位刻
      fadein: 20
      # 停留, 单位刻
      stay: 10
      # 淡出, 单位刻
      fadeout: 20
  # 格式2
  - title: title
```

## `walkSpeed`

### 效果

设置玩家行走速度

### 示例

```yaml
on_allow:
  # 在原有的基础上增加行走速度
  - walkSpeed: +5
  # 在原有的基础上减少行走速度
  - walkSpeed: -5
  # 设置行走速度
  - walkSpeed: 5
```

## `block`

### 效果

设置世界中的方块

### 示例

```yaml
on_allow:
  - block:
      world: world
      x: 100
      y: 100
      z: 100
      type: STONE
```