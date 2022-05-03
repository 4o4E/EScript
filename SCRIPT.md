# 条件

- 判断背包空格子数量大于给定数值: `invHasSpace: 20`
- 判断背包空格子数量小于给定数值: `invHasNotSpace: 20`
- 判断Vault经济余额大于给定数值: `hasMoney: 100`
- 判断Vault经济余额小于给定数值: `hasNotMoney: 100`
- 判断玩家拥有权限

  格式1: `hasPerm: perm.example`

  格式2:
  ```yml
    hasPerm:
      - perm.example.1
      - perm.example.2
  ```
- 判断玩家没有权限

  格式1: `hasNotPerm: perm.example`

  格式2:
  ```yml
    hasNotPerm:
      - perm.example.1
      - perm.example.2
  ```
- 判断玩家在指定世界内

  格式1: `inWorld: world`

  格式2:
  ```yml
    inWorld:
      - world
      - world_nether
      - world_the_end
  ```
- 判断玩家在指定世界外

  格式1: `notInWorld: world`

  格式2:
  ```yml
    notInWorld:
      - world
      - world_nether
      - world_the_end
  ```
- 判断玩家x坐标在指定范围内: `xInRange: -10.0..10.0`
- 判断玩家x坐标在指定范围外: `xOutRange: -10.0..10.0`
- 判断玩家y坐标在指定范围内: `yInRange: -10.0..10.0`
- 判断玩家y坐标在指定范围外: `yOutRange: -10.0..10.0`
- 判断玩家z坐标在指定范围内: `zInRange: -10.0..10.0`
- 判断玩家z坐标在指定范围外: `zOutRange: -10.0..10.0`
- 自定义判断: `check: %player_name% == 404E`(可用: `==`, `!=`, `>=`, `<=`, `>`, `<`, 支持PlaceholderAPI)
- 有药水效果

  格式1: `hasEffect: SLOW`

  格式2:
  ```yml
    hasEffect:
      type: SLOW
      level: 1
  ```
- 没有药水效果

  格式1: `hasNotEffect: SLOW`

  格式2:
  ```yml
    hasNotEffect:
      type: SLOW
      level: 1
  ```
- 生命值在范围内: `headlthInRange: 2..10`
- 生命值在范围外: `headlthOutRange: 4..6`
- 饥饿值在范围内: `hungerInRange: 0..5`
- 饥饿值在范围外: `hungerOutRange: 0..5`
- 持有物品

  格式1: `hasItem: golden apple`

  格式2:
  ```yml
  hasItem:
    type: golden apple
    name: xxx
    amount: 10
    lore:
      - awa
    enchant:
      unbreaking: 1
  ```
- 自定义cd
- 经验值

# 执行

- 发送消息: `msg: 你是一个一个一个一个`
- 发送Title:
  ```yml
  title:
    title: title line # 标题
    subtitle: subtitle line # 副标题, 不写则为空
    fadein: 20 # 淡入, 单位刻, 不写默认20
    stay: 20 # 停留, 单位刻, 不写默认20
    fadeout: 20 # 淡出, 单位刻, 不写默认20
  ```
- 以玩家身份执行指令: `command: say hallo`
- 以OP身份执行指令(不推荐使用, 能用console执行的指令就不要用op执行): `op: time set day`
- 以控制台身份执行指令: `console: tell %player_name% 你是一个一个一个一个`
- 播放声音:
  ```yml
  sound:
    type: block.anvil.place
    volume: 1.0
    pitch: 1.0
  ```
- 播放粒子效果
  ```yml
  particle:
    type: END_ROD
    x: %math_1_({player_x}+1)%
    y: %math_1_({player_y}+1)%
    z: %math_1_({player_z}+1)%
    count: 10
    offset_x: 0.5
    offset_y: 0.5
    offset_z: 0.5
    extra: 1.0
    data:
      color: #ffcccc
      size: 2
    #data: STONE_BRICK
  ```
- 设置玩家向量
  ```yml
  vector:
    x: 1.1
    y: 2.2
    z: 3.3
  ```
- 设置行走速度 `walkSpeed: 5`
- 设置生命值 `health: 20.0`
- 设置生命上限 `maxHealth: 40.0`
- 设置饥饿值 `hunger: 20` // +10
- 设置饱和度 `saturation: 20`
- 设置经验值 `level: 10` 10L -10 -10L
- 添加药水效果
  ```yml
  addPotion:
    type: SLOW
    level: 1
    duration: 600
  ```
- 移除药水效果 `delPotion: SLOW`
- 延迟 `delay: 20`
- 自定义cd
  ```yml
  cooldown:
    type: add # set / reset
    name: example_cd
    value: 1000
  ```
- 给钱: `giveMoney: 10`
- 扣钱: `takeMoney: 10`
- 执行脚本: `script: <name>`
- 给予物品

  格式1: `giveItem: golden apple`

  格式2:
  ```yml
  giveItem:
    type: golden apple
    lore:
      - awa
    enchant:
      unbreaking: 1
    drop_on_full: true 
  ```
