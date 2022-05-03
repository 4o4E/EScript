# 判断

## `check`

### 别名

- `condition`

### 效果

比较数值大小或或字符串是否相等

### 示例

```yaml
condition:
  - check: %player_health% > 5
  - check: %player_health% >= 5
  - check: %player_health% == 5
  - check: %player_health% != 5
  - check: %player_health% <= 5
  - check: %player_health% < 5
```

## `incooldown`

### 别名

- `inCd`
- `inCooldown`

### 效果

检查是否在自定义cd中

### 示例

```yaml
condition:
  - inCd: 自定义cd名字
```

## `outcd`

### 别名

- `notIncd`
- `outCooldown`
- `notIncooldown`

### 效果

检查是否不在自定义cd中

### 示例

```yaml
condition:
  - outCd: 自定义cd名字
```

## `hasEffect`

### 效果

检查是否拥有药水效果

### 示例

```yaml
condition:
  # 写法1
  # 会检查药水效果等级
  - hasEffect:
      type: SLOW
      level: 1
  # 写法2
  - hasEffect: SLOW
```

## `hasNotEffect`

### 效果

检查是否不拥有药水效果

### 示例

```yaml
condition:
  # 写法1
  # 会检查药水效果等级
  - hasNotEffect:
      type: SLOW
      level: 1
  # 写法2
  - hasNotEffect: SLOW
```

## `allowFly`

### 别名

- `canFly`
- `allowFlight`
- `canFlight`

### 效果

检查是否允许飞行

### 示例

```yaml
condition:
  - allowFly: SLOW
```

## `denyFly`

### 别名

- `cannotFly`
- `denyFlight`
- `cannotFlight`

### 效果

检查是否不允许飞行

### 示例

```yaml
condition:
  - denyFly: SLOW
```

## `healthInRange`

### 效果

检查生命值是否在范围内

### 示例

```yaml
condition:
  - healthInRange: 5.0..10.0
```

## `healthOutRange`

### 别名

- `healthNotInRange`

### 效果

检查生命值是否在范围外

### 示例

```yaml
condition:
  - healthOutRange: 5.0..10.0
```

## `hungerInRange`

### 效果

检查饥饿值是否在范围内

### 示例

```yaml
condition:
  - hungerInRange: 5.0..10.0
```

## `hungerOutRange`

### 别名

- `hungerNotInRange`

### 效果

检查饥饿值是否在范围外

### 示例

```yaml
condition:
  - hungerOutRange: 5.0..10.0
```

## `invHasSpace`

### 效果

检查背包是否有足够的空位

### 示例

```yaml
condition:
  - invHasSpace: 5
```

## `invHasNotSpace`

### 效果

检查背包是否没有足够的空位(空位数量少于xxx)

### 示例

```yaml
condition:
  - invHasNotSpace: 5
```

## `hasItem`

### 效果

检查是否持有指定物品

### 示例

```yaml
condition:
  # 写法1
  # 会检查详细信息
  - hasItem:
      type: golden apple
      name: xxx
      amount: 10
      lore:
        - awa
      enchant:
        unbreaking: 1
  # 写法2
  - hasItem: golden apple
```

## `hasNotItem`

### 效果

检查是否未持有物品

### 示例

```yaml
  # 写法1
  # 会检查详细信息
  - hasNotItem:
      type: golden apple
      name: xxx
      amount: 10
      lore:
        - awa
      enchant:
        unbreaking: 1
  # 写法2
  - hasNotItem: golden apple
```

## `hasMoney`

### 别名

- `hasBal`
- `basEco`

### 效果

检查是否有足够的余额

### 示例

```yaml
condition:
  - hasBal: 200.0
```

## `hasNotMoney`

### 别名

- `hasNotBal`
- `hasNotEco`

### 效果

检查是否没有足够的金钱

### 示例

```yaml
condition:
  - hasNotBal: 200.0
```

## `isOP`

### 效果

检查是否是op

### 示例

```yaml
condition:
  - isOP
```

## `isNotOP`

### 别名

- `notOp`

### 效果

检查是否不是op

### 示例

```yaml
condition:
  - notOp
```

## `hasPerm`

### 效果

检查是否拥有权限

### 示例

```yaml
condition:
  - hasPerm: perm.example
```

## `hasNotPerm`

### 别名

- `notHasPerm`
- `noPerm`

### 效果

检查是否未拥有权限

### 示例

```yaml
condition:
  - noPerm: perm.example
```

## `inWorld`

### 效果

检查是否在指定世界内

### 示例

```yaml
condition:
  # 格式1
  # 只要在以下任意一个世界内就判断为true
  - inWorld:
      - world
      - world_nether
      - world_the_end
  # 格式2
  - inWorld: world
```

## `notInWorld`

### 别名

- `outWorld`

### 效果

检查是否不在世界中

### 示例

```yaml
condition:
  # 格式1
  # 不在以下任意一个世界内才判断为true
  - outWorld:
      - world
      - world_nether
      - world_the_end
  # 格式2
  - outWorld: world
```

## `xInRange`

### 效果

检查x坐标是否在指定范围内

### 示例

```yaml
condition:
  - xInRange: -10.0..10.0
```

## `xOutRange`

### 效果

检查x坐标是否在指定范围外

### 示例

```yaml
condition:
  - xOutRange: -10.0..10.0
```

## `yInRange`

### 效果

检查y坐标是否在指定范围内

### 示例

```yaml
condition:
  - yInRange: -10.0..10.0
```

## `yOutRange`

### 效果

检查y坐标是否在指定范围外

### 示例

```yaml
condition:
  - yOutRange: -10.0..10.0
```

## `zInRange`

### 效果

检查z坐标是否在指定范围内

### 示例

```yaml
condition:
  - zInRange: -10.0..10.0
```

## `zOutRange`

### 效果

检查z坐标是否在指定范围外

### 示例

```yaml
condition:
  - zOutRange: -10.0..10.0
```