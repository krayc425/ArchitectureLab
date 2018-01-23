# 作业 2

### 1. 请阅读 Moodle 上的库 Visitor 模式样例 `/sklnst.jar` 中的代码，并按照如下语法用这种“自定义 While 语言”写一个小程序来计算输入值 x 的取整平方根。

![](https://ws2.sinaimg.cn/large/006tKfTcgy1fnfd6590d9j30xw092go5.jpg)

该程序输出值 r 的数学描述如下:

![](https://ws2.sinaimg.cn/large/006tKfTcgy1fnfd6uu0ybj305o02kglj.jpg)

提示：可以用牛顿法来实现这一程序,迭代获得需要的结果,针对这一问题的牛顿法迭代公式如下:

![](https://ws1.sinaimg.cn/large/006tKfTcgy1fnfd70zxrij30900880sv.jpg)

请使用 `sklnst.jar` 中的解释器 `cn.edu.nju.sklnst.whilex.eval.Interpreter` 来运行完成的小程序 `test.whl`。

```
x = 12345;  // Your number
r = x;
y = 0;
while not r = y do 
    y = r;
    r = (r + x / r) / 2;
end
```

### 2. 请使用 Moodle 上的库函数来构建一个 Java 程序, 用于记录提取“自定义 While 语言”小程序中的变量。请使用 Visitor 设计模式来完成这一程序。

提示：当同学们完成 `CountVariables` 的编写后，示例运行效果如下：

```
java cn.edu.nju.sklnst.whilex.analysis.CountVariables test.whl test.whl has 3 variables:
x y r
```

