# Ch7 深度学习框架

## 原理

### 神经元

![](https://ws2.sinaimg.cn/large/006tNc79ly1fq2x56zy00j31kw0une81.jpg)

### 梯度下降

### 激活函数

* Sigmoid
    * 激活函数计算量大（指数运算），反向传播求误差梯度时，求导涉及除法
    * 对于深层网络，Sigmoid 函数反向传播时，很容易就会出现**梯度消失**的情况（在 Sigmoid 接近饱和区时，变换太缓慢，导数趋于 0，这种情况会造成信息丢失），从而无法完成深层网络的训练
* Tanh
* **ReLU**
    * Sigmoid 等激活函数（指数运算）计算量大，并且在深层网络上容易出现梯度消失问题
    * ReLU 计算量小（不涉及除法），一部分神经元的输出为 0 造成了网络的稀疏性，并且减少了参数的相互依存关系，缓解了过拟合问题的发生

## TensorFlow

### 示例

![TensorFlow计算流图示例](http://dingyue.nosdn.127.net/VZ2h00kNetSAbaAOKZxDZ49RMQ1kCWAOL4mL0E4MqzwOv1509414674521transferflag.png)

如上图所示是一个简单线性模型的 TF 正向计算图和反向计算图。图中`x`是输入，`W`是参数权值，`b`是偏差值，`MatMul`和`Add`是计算操作，`dMatMul`和`dAdd`是梯度计算操作，`C`是正向计算的目标函数，`1`是反向计算的初始值，`dC/dW`和`dC/dx`是模型参数的梯度函数。

以上图为例实现的 TF 代码见下。首先声明参数变量 `W`、`b` 和输入变量 `x`，构建线性模型`y=W*x+b`，目标函数 `loss` 采用误差平方和最小化方法，优化函数 `optimizer` 采用随机梯度下降方法。然后初始化全局参数变量，利用 `session` 与 `master` 交互实现图计算。

![](http://dingyue.nosdn.127.net/V49GTAsNfOWAaY3Wt3KMkxolYHHB3tH6NzPJEGYbFzHsu1509414674521transferflag.png)
![](http://dingyue.nosdn.127.net/AkqRT5RADvd=AUEc8CMZWV5g7RgLnQU16Zm9ekzIqiqT11509414695801transferflag.png)

代码中 `summary` 可以记录 `graph` 元信息和 `tensor` 数据信息，再利用 Tensorboard 分析模型结构和训练参数。

下面是上述代码在 Tensorboard 中记录下的 Tensor 跟踪图。Tensorboard 可以显示 scalar 和 histogram 两种形式。跟踪变量走势可更方便的分析模型和调整参数。

![](http://dingyue.nosdn.127.net/jBO7eHrxuw9uV1sBdDo2wdnpnoJXsjpuxjrOLxNamduVh1509414674521transferflag.png)

下图是该示例在 Tensorboard 中显示的 graph 图。左侧子图描述的正向计算图和反向计算图，正向计算的输出被用于反向计算的输入，其中 `MatMul` 对应 `MatMul_grad`，`Add` 对应 `Add_grad` 等。右上侧子图指明了目标函数最小化训练过程中要更新的模型参数`W`、`b`，右下侧子图是参数节点`W`、`b`展开后的结果。

![](http://dingyue.nosdn.127.net/EC=gruDpYHSMcRTfPNNT1F=Al=xWpZPfu6H9lgDyObzV91509414674521transferflag.png)

### 数据流图

TensorFlow 的数据流图（data flow graph）符号化地表示了模型的计算是如何工作的。

简单地说，数据流图是完整的 TensorFlow 计算，如图所示。图中的节点（node）表示操作（operation），而边（edge）表示各操作之间流通的数据。

![](http://www.10tiao.com/img.do?url=http%3A//mmbiz.qpic.cn/mmbiz_png/iaibvmyz4605On4WXYkORiaamGP38W9V3ct3Y2A7qLTMeuyGGQibWTowk0yrCp9mHc5aSMwRpX9FHpCp55OcyJwMibA/0%3Fwx_fmt%3Dpng)

通常，节点实现数学运算，同时也表示数据或变量的供给（feed），或输出结果。

边描述节点之间的输入/输出关系。这些数据边缘专门传输张量。节点被分配给计算设备，并且一旦其输入边缘上的所有张量都到位，则开始异步地并行执行。

所有的操作（operation）都拥有一个名字，可以表示一个抽象的计算（例如，矩阵求逆或者相乘）。

* **边**代表数据流
* **节点**代表操作
    * 数学运算（Add, Subtract, Multiply, Div, Exp, Log, Greater, Less, Equal...）
    * 数组运算（Concat, Slice, Split, Constant, Rank, Shape, Shuffle...）
    * 矩阵运算（MatMul, MatrixInverse, MatrixDeterminant...）
    * 有状态的（Variable, Assign, AssignAdd...）
    * 神经网络构建（SoftMax, Sigmoid, ReLU, Convolution2D, MaxPool...）
    * 检查点（Save, Restore）
    * 队列和同步（Enqueue, Dequeue, MutexAcquire, MutexRelease...）
    * 控制张量流动的（Merge, Switch, Enter, Leave, NextItertion）

[参考](https://zhuanlan.zhihu.com/p/26759381)

### 会话

```Python
with tf.Session() as sess:
    result = sess.run([product])
    print result
```

### 设备

```Python
with tf.Session() as sess:
    # 指定在第二个 GPU 上运行
    with tf.device("/gpu:1"):
        matrix1 = tf.constant([[3., 3.]])
        matrix2 = tf.constant([[2.],[2.]])
        product = tf.matmul(matrix1, matrix2)
```

### 整体框架

![](https://ws2.sinaimg.cn/large/006tNc79gy1fpzw440erwj31kw15ywuz.jpg)

![](http://dingyue.nosdn.127.net/182K5hU4qPXwtfOfz7xdP0kdORwLRwwyOHUi9u7ShA60x1509414329829transferflag.png)

* 从底向上分为设备管理和通信层、数据操作层、图计算层、API 接口层、应用层。其中设备管理和通信层、数据操作层、图计算层是 TF 的核心层。

* 底层设备通信层负责网络通信和设备管理。设备管理可以实现 TF 设备异构的特性，支持 CPU、GPU、Mobile 等不同设备。网络通信依赖 gRPC 通信协议实现不同设备间的数据传输和更新。

* 第二层是 Tensor 的 OpKernels 实现。这些 OpKernels 以 Tensor 为处理对象，依赖网络通信和设备内存分配，实现了各种 Tensor 操作或计算。Opkernels 不仅包含 MatMul 等计算操作，还包含 Queue 等非计算操作。

* 第三层是图计算层（Graph），包含本地计算流图和分布式计算流图的实现。Graph 模块包含 Graph 的创建、编译、优化和执行等部分，Graph 中每个节点都是 OpKernels 类型表示。

* 第四层是API接口层。Tensor C API 是对 TF 功能模块的接口封装，便于其他语言平台调用。

* 第四层以上是应用层。不同编程语言在应用层通过 API 接口层调用 TF 核心功能实现相关实验和应用。

# 参考链接

* [『深度长文』Tensorflow代码解析（一）](https://www.jiqizhixin.com/articles/2017-10-31-2)
* [『深度长文』Tensorflow代码解析（二）](https://www.jiqizhixin.com/articles/2017-10-31-4)
* [机器学习笔记](https://feisky.xyz/machine-learning/)

