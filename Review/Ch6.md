# Ch6 符号执行

## 状态

每个状态记录执行过程中的一条**程序路径**

## 状态的内容 / 执行状态ES的内容（背出）

* PC 计数器
* 符号（符号变量）
* 具体变量和具体值
* 约束（Constraint）

## 算法流程（理解）

![](https://github.com/songkuixi/ArchitectureLab/blob/master/Review/Ch6_1.png)

## 符号执行的内存管理（理解）

![](https://github.com/songkuixi/ArchitectureLab/blob/master/Review/Ch6_2.png)

示例代码，对照 PPT 查看五个标注点的内存状态

```C
make_symbolic(x);
make_symbolic(y);       // 1
z=x+y;                  // 2
if (z == 0)
    y=z+y;              // 3
   return y;
if (z < 0)
    x=x-z;              // 4
   return x;
 else
   z=0;                 // 5
   return z-x;  
```

## 第二种设计架构（理解）

* 不记录状态
* 符号执行与具体执行同时进行
* 每次先具体执行一条路径，再由符号执行求解出另外路径的输入，来执行另外一条路径……直到所有路径都被执行到为止

