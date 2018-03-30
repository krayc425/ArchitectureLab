# 作业 3

1. 以下为一段计算整数相除的代码 `divide`，其被除数为参数变量 `x`，除数为参数变量 `y`。我们希望用 `KLEE` 来构建符号执行过程，将函数 `divide` 的参数 `x` 和 `y` 初始化为符号，并标记成 `sx`，`sy`。
    1. 请写出符合以上要求的 `main` 函数。
    2. 请写出使得 `divide` 函数中的 `while` 循环执行三次(即循环体被执行了 3 遍), 并使得 `divide` 函数返回时的条件约束，并给出化简后的形式。(提醒，仅需写 出这一个符号执行状态的条件约束)
    
    ```
    struct resulttype {
      int quot;
      int rem;
    };
    resulttype divide (int x, int y)
    {
      resulttype re;
      re.quot = 0;
      re.rem = x;
      while(re.rem > y){
    ++re.quot;
    re.rem -= y; }
    }
    ```

2. 当采用梯度下降法来训练神经网络时，假如发现当前参数无论如何微调，都会造成输出误差(loss 值)增加，请问此时你会采用怎样的进一步策略? 请详细叙述原因。

3. 请详细说明深度神经网络在设计上采用 ReLU 作为激活函数的原因。

4. 请仔细阅读以下 Tensorflow 程序, 并绘制对应的数据流图。

    ```
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


