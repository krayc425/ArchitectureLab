# 作业 1

### 请结合自己的认识，谈一谈对于软件设计中功能集成的看法。你认为把尽可能多的功能集成到一个大型软件中是种好的设计还是不好的设计，请给出明确理由和具体实例。

我觉得把尽可能多的功能集成到一个大型软件中不是一个特别好的设计。原因如下：

1. 从用户体验来看，如果把特别多的功能都集成在一起，那么会让这个软件的**主要功能/目的变得不太明确**。在这一点上，最值得比较的就是支付宝和微信两个 App 了：
<table>
    <td>
        <img src="https://ws3.sinaimg.cn/large/006tKfTcgy1fnfbgs2cxnj30v91voke7.jpg" style="display: inline-block" />    
    </td>
    <td>
        <img src="https://ws1.sinaimg.cn/large/006tKfTcgy1fnfbgqv8ajj30v91vo47e.jpg" style="display: inline-block" />    
    </td>
</table>

可以看出，支付宝这么一个以**支付**为主要需求的 App，在首页却充斥着各种与支付无关的内容：共享单车、电影票、快递、充值中心，更别提中间占大比例的滑动 Banner 和兑换券、红包的功能。下面的 TabBar 也是，除了首页外，还有口碑、朋友等跟支付可以说是相距甚远的功能。而支付宝竟然把它们都做到了底部栏，认为这些都是较为重要的功能点。
  
反观微信，它是一个**社交**功能 App，所以它的四个 TabBar 项目都跟社交有关：聊天列表、联系人列表、发现（其实就是朋友圈入口），以及“我”这个设置页。从页面占比跟主要功能点的比例来看，微信显然高于支付宝。而且，微信也有许多支付宝也有的功能，比如手机充值、购物等，只是它把入口藏在了较深的地方，让一些深度用户可以去寻找、探索它们。因为微信很清楚，它的主要功能就是聊天，其它的都是一些辅助或者提升品牌忠诚度的行为。
    
虽然支付宝和微信不能算特别大型的软件（相比 Office 套件等），但是从他们的页面布局也能管中窥豹。对于一个 App 或软件，我觉得他应该把自己的首要目标做好，并且以它为重要推广点，且始终将它放在最重要的位置。毕竟大家使用这些软件，最常用的场景还是当符合它们的首要目标时，比如打开支付宝就是要付钱。增加太多功能在同一个软件中反而会增加用户的困惑。

2. 从软件设计架构来看，如果把特别多的功能都集成在一起，可能会导致软件架构太过复杂，模块之间可复用性低。

还是以刚才的例子来看，比如支付宝要集成聊天功能，且聊天并不是一个非常小的模块，那么开发团队要投入较多的精力于其上。除非有现成的第三方库可以集成（当然这也有很多缺点，比如版本不兼容、代码不稳定性），那么还是需要很长时间来维护和调整的。而且，由于它跟主功能支付关系不大，那么几乎所有的代码都要重写，不能复用已有的模块。另外不管是采用什么架构，都会不可避免地增加展示层、逻辑层和数据层的耦合程度，这会让日后的维护成本大大提高。
