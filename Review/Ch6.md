# Ch6 符号执行

## 定义

* J.C. King 于 1976 年提出的方法 Symbolic Execution，基本思想是用符号值代替具体值（实际数据）作为输入（或者中间变量）来执行程序。
* 程序的输出（或者其它变量）常常是一个符号表达式。

## 用途

* 自动生成测试用例（程序输入)
* 提取变量之间的逻辑关系

## 状态

每个状态记录执行过程中的一条**程序路径**

## 状态的内容（背出）

* PC 计数器
* 符号（符号变量）
* 具体变量和具体值
* 约束（Constraint）

## 算法流程（理解）

![](https://ws1.sinaimg.cn/large/006tNc79gy1fpzkuldxvlj31kw15htw1.jpg)

## 符号执行的内存管理（理解）

* 具体值可以放在寄存器里或内存里
* 符号只能放在内存里
* PC 指向指令

![](https://ws3.sinaimg.cn/large/006tNc79gy1fpzkuw2smnj31kw15hh30.jpg)

示例代码，对照 PPT 查看五个标注点的内存状态

```C
make_symbolic(x);
make_symbolic(y);       // 1
z=x+y;                  // 2
if (z == 0)
    y = z + y;          // 3
   return y;
if (z < 0)
    x = x - z;          // 4
   return x;
 else
   z = 0;               // 5
   return z - x;  
```

## 第二种设计架构（理解）

* 不记录状态
* 符号执行与具体执行同时进行
* 每次先具体执行一条路径，再由符号执行求解出另外路径的输入，来执行另外一条路径……直到所有路径都被执行到为止

