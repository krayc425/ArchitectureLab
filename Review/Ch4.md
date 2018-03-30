# Ch4 视图

## 4+1模型

**注：除了以下文字概念外，还需参考 PPT 上四种进程的关系图及符号标记方法。**

### 概述

Kruchten 1995年提出

* 基本思想：单个视图难以全面描述，“横看成岭竖成峰，远近高低各不同”
* 解决方案：多个视图来刻画同一个体系结构

### 逻辑视图

* 强调系统的功能性需求
* 使用类、对象来表示。从问题域出发，采取面向对象分析方法，按照抽象、封装、继承的原则，获得代表问题抽象的对象和类。
     
### 进程视图
     
* 关注系统的非功能性需求，如性能，可用性。强调系统的并发性、分布性、容错，以及逻辑视图的类如何对应到进程。
* 进程是由一系列的任务构成的执行单元。软件被分解成为众多的任务，这些任务分成两种:主任务和辅任务。主任务针对软件的主要功能，辅任务是为了完成主任务而引入的，如缓存、延时等。
* 主任务之间的通信方式包括基于消息的 同步/异步、远程过程调用、事件广播等。
* 辅任务之间通信主要通过共享内存等方式。

### 开发视图

* 强调在开发环境下，如何将软件划分成为不同的模块或者子系统，以便分配给程序员或团队开发。
* 通常这样的子系统都是以层次方式组织，每一层通过接口给上一层提供服务。
* 开发视图遵循的三大原则：分割、编组、可视化。
* 设计开发视图的时候主要考虑到以下因素：开发的难易程度、软件管理、重用和通用性、工具及开发语言的限制。
                  
### 物理视图

* 主要考虑如何将软件映射到不同的物理硬件上去。

### 场景视图

**场景（用例）是把前4个视图串联在一起的纽带，而且不仅仅如此！**

* 场景是发现软件体系结构元素的驱动者。
* 场景是体系结构设计结束后验证和演示的最好角色，不管是书面形式还是作为测试体系结构原型的起始点。
