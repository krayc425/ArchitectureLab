# 作业 3

### 1. 以下为一段计算整数相除的代码 `divide`，其被除数为参数变量 `x`，除数为参数变量 `y`。我们希望用 KLEE 来构建符号执行过程，将函数 `divide` 的参数 `x` 和 `y` 初始化为符号，并标记成 `sx`，`sy`。

```C
struct resulttype {
   int quot;
   int rem;
};
resulttype divide (int x, int y) {
   resulttype re;
   re.quot = 0;
   re.rem = x;
   while (re.rem > y) {
       ++re.quot;
       re.rem -= y; 
   }
}
```
    
#### 1) 请写出符合以上要求的 `main` 函数。

```C
int main() {
  int x, y;
  klee_make_symbolic(&x, sizeof(x), "sx"); 
  klee_make_symbolic(&y, sizeof(y), "sy");
  return divide(x, y);
}
```

#### 2) 请写出使得 `divide` 函数中的 `while` 循环执行三次（即循环体被执行了 3 遍） 并使得 `divide` 函数返回时的条件约束，并给出化简后的形式。（提醒：仅需写出这一个符号执行状态的条件约束）
    
若要让`while`循环只循环三次，那么只要让 `x` 落在 `[3y, 4y)` 的区间内即可。而且，如果 `x` 和 `y` 都是负数，那么 `while` 循环将无法结束。综上，可以给出约束为：`x >= 3 * y && x < 4 * y && y > 0`。

### 2. 当采用梯度下降法来训练神经网络时，假如发现当前参数无论如何微调，都会造成输出误差（loss 值）增加，请问此时你会采用怎样的进一步策略? 请详细叙述原因。

* 可能因为$\alpha$值过大，导致每次都跳过了唯一的最小极值。
* 可能因为数据数量级相差太大，可以使用正则化的方式调整参数。
* 可能因为数据样本量太小。

#### 参考：薛老师的答案

1. 使用不同的初始化参数重新训练，梯度下降可能找到新的局部极小值，可能比当然局部极小值更优。
2. 使用类似模拟退火算法的方法，向各种方向随机前进，或许可以跳出当前的局部极小值点，再次使用梯度下降法找到更优的极小值点。
3. 使用不同的优化器（带动量，自动调节学习率等）进行梯度下降，试图越过局部极小值点，找到更优解。
4. 增加神经网络层数或神经元个数来避免欠拟合的情况或者减少神经网络的层数或神经元个数来避免过拟合的情况，可以进一步减小 loss 值。
5. 通过 PCA 降维的方式或特征选择的方式预处理特征，排除冗余特征，提高神经网络性能，或许可以找到更好的极小值。
6. 挖掘数据的更多特征并将之作为神经网络的输入，帮助神经网络更全面的了解数据特征，更好的拟合数据。
7. 调节每次计算梯度的样本的数量（batch），比如使用更小的 batch 或直接使用带有一定随机性的随机梯度下降（SGD），也许可以找到更好的极小值点。

### 3. 请详细说明深度神经网络在设计上采用 ReLU 作为激活函数的原因。

相比于传统的神经网络激活函数，诸如逻辑函数（Logistic sigmoid）和`tanh`等双曲函数，线性整流函数有着以下几方面的优势：

* 仿生物学原理：相关大脑方面的研究表明生物神经元的信息编码通常是比较分散及稀疏的。通常情况下，大脑中在同一时间大概只有 1%-4% 的神经元处于活跃状态。使用线性修正以及正则化（regularization）可以对机器神经网络中神经元的活跃度（即输出为正值）进行调试；相比之下，逻辑函数在输入为 0 时达到 $\frac{1}{2}$，即已经是半饱和的稳定状态，不够符合实际生物学对模拟神经网络的期望。不过需要指出的是，一般情况下，在一个使用修正线性单元（即线性整流）的神经网络中大概有 50% 的神经元处于激活态。
* 更加有效率的梯度下降以及反向传播：避免了**梯度爆炸**和**梯度消失**问题
* 简化计算过程：没有了其他复杂激活函数中诸如指数函数的影响；同时活跃度的分散性使得神经网络整体**计算成本下降**

### 4. 请仔细阅读以下 TensorFlow 程序, 并绘制对应的数据流图

```Python
xs = tf.placeholder(tf.float32, [None, 1], name="xs") 
ys = tf.placeholder(tf.float32, [None, 1], name="ys")
    
Weights1 = tf.Variable(tf.constant([[0.0004]]),dtype =tf.float32, name="Weights1")
Biases1 = tf.Variable(tf.zeros([1,1])+0.1,dtype =tf.float32, name="Biases1") 
Wx_plus_b1 = tf.add(tf.matmul(xs, Weights1, name="matmul"), Biases1, name="add")
l1 = tf.nn.sigmoid(Wx_plus_b1)
    
Weights2 = tf.Variable(tf.constant([[10000.0]]),dtype =tf.float32, name="Weights2")
Biases2 = tf.Variable(tf.constant(-4999.5),dtype =tf.float32, name="Biases2")
Wx_plus_b2 = tf.add(tf.matmul(l1, Weights2, name="matmul"), Biases2, name="add")
    
prediction = Wx_plus_b2
    
loss = tf.reduce_mean(tf.square(tf.subtract(ys, prediction, name="Sub"), name="Square"), name="ReduceMean")
    
train_step = tf.train.GradientDescentOptimizer(0.1).minimize(loss, name="minimize")
```

#### 数据流图

![](https://ws2.sinaimg.cn/large/006tNc79gy1fpztt5431dj319y1is7eh.jpg)

![](https://ws4.sinaimg.cn/large/006tNc79gy1fpztt7spb2j31540jgq74.jpg)


