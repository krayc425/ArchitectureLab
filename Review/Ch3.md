# Ch3 设计模式

## 概念

设计模式是：对被用来在特定场景下解决一般设计问题的类和相互通信的对象的描述。

### 四个基本要素（考点）

* 模式名称（Pattern Name）：一个助记名，它用一两个词来描述模式的问题、解决方案和效果。
* 问题（Problem）: 描述了应该在何时使用模式。
* 解决方案（Solution）：描述了设计的组成部分，它们之间的相互关系和各自的职责和协作方式。
* 效果（Consequence）：描述了模式应用的效果及使用模式应该权衡的问题。

## 访问者模式（Visitor Pattern）（重点★）

### 动机

* 对于系统中的某些对象，它们存储在同一个集合中，且具有不同的类型，而且对于该集合中的对象，可以接受一类称为访问者的对象来访问，而且不同的访问者其访问方式有所不同，访问者模式为解决这类问题而诞生。
* 在实际使用时，对同一集合对象的操作并不是唯一的 ，对相同的元素对象可能存在多种不同的操作方式。
* 而且这些操作方式并不稳定，可能还需要增加新的操作，以满足新的业务需求。
* 此时，访问者模式就是一个值得考虑的解决方案。

### 定义

* 表示一个作用于某对象结构中的各元素的操作。它使我们可以在不改变各元素的类的前提下定义作用于这些元素的新操作。
* 访问者模式是一种对象行为型模式。

### 结构

![](https://ws4.sinaimg.cn/large/006tNc79gy1fpzksm6tchj31kw159dsy.jpg)

### 参与者

* Vistor：抽象访问者
* ConcreteVisitor：具体访问者 
* Element：抽象元素
* ConcreteElement：具体元素
* ObjectStructure：对象结构

### 实现

```Java
abstract class Element {
    // Methods
    abstract public void Accept(Visitor visitor); 
}

class ConcreteElementA : Element {
    // Methods
    override public void Accept(Visitor visitor) {
        visitor.VisitConcreteElementA(this);
    }
    public void OperationA() {
    }
}

class ConcreteElementB : Element {
    // Methods
    override public void Accept(Visitor visitor) {
        visitor.VisitConcreteElementB(this);
    }
    public void OperationB() {
    }
}

class ObjectStructure {
    // Fields
    private ArrayList elements = new ArrayList();
    // Methods
    public void Attach(Element element) {
        elements.Add(element); 
    }
    public void Detach(Element element) {
        elements.Remove(element); 
    }
    public void Accept(Visitor visitor) {
        foreach (Element e in elements) {
            e.Accept(visitor);
        }
    } 
}

abstract class Visitor {
    // Methods
    abstract public void VisitConcreteElementA(ConcreteElementA concreteElementA);             
    abstract public void VisitConcreteElementB(ConcreteElementB concreteElementB);
}

class ConcreteVisitor1 : Visitor {
    // Methods
    override public void VisitConcreteElementA(ConcreteElementA concreteElementA) {
        Console.WriteLine("{0} visited by {1}", concreteElementA, this); 
    }
    override public void VisitConcreteElementB(ConcreteElementB concreteElementB) {
        Console.WriteLine("{0} visited by {1}", concreteElementB, this); 
    }
}

class ConcreteVisitor2 : Visitor {
    // Methods
    override public void VisitConcreteElementA(ConcreteElementA concreteElementA) {
        Console.WriteLine("{0} visited by {1}", concreteElementA, this); 
    }
    override public void VisitConcreteElementB(ConcreteElementB concreteElementB) {
        Console.WriteLine("{0} visited by {1}", concreteElementB, this); 
    }
}

static void Main(string[] args) {
    // Setup structure
    ObjectStructure o = new ObjectStructure(); 
    o.Attach(new ConcreteElementA());    
    o.Attach(new ConcreteElementB());
  
    // Create visitor objects
    ConcreteVisitor1 v1 = new ConcreteVisitor1(); 
    ConcreteVisitor2 v2 = new ConcreteVisitor2();

    // Structure accepting visitors
    o.Accept(v1);
    o.Accept(v2);

    Console.ReadKey(); 
}
```

### 优点

* 使得增加新的操作变得很容易。如果一些操作依赖于一个复杂的结构对象的话，那么一般而言，增加新的操作会很复杂。而使用访问者模式，增加新的操作就意味着增加一个新的访问者类，因此将变得很容易。
* 将有关的行为集中到一个访问者对象中，而不是分散到一个个的节点类中。
* 可以跨过几个类的等级结构访问属于不同的等级结构的成员类。

### 缺点

* 破坏封装。访问者模式要求访问者对象访问并调用每一个节点对象的操作，这隐含了一个对所有节点对象的要求:它们必须暴露一些自己的操作和内部状态。不然，访问者的访问就变得没有意义。由于访问者对象自己会积累访问操作所需的状态，从而使这些状态不再存储在节点对象中，这也是破坏封装的。
* 增加新的节点类变得很困难。每增加一个新的节点都意味着要在抽象访问者角色中增加一个新的抽象操作，并在每一个具体访问者类中增加相应的具体操作。

### 使用

* 定义对象结构的类很少改变，但经常需要在此结构上定义新的操作。
* 对象需要添加很多不同的并且不相关的操作，而我们想避免让这些操作“污染”这些对象的类。访问者模式使得我们可以将相关的操作集中起来定义在一个类中。当该对象结构被很多应用共享时，用访问者模式让每个应用仅包含需要用到的操作。

### 应用

* 访问者模式中对象结构存储了不同类型的元素对象，以供不同访问者访问。访问者模式包括两个层次结构，一个是访问者层次结构，提供了抽象访问者和具体访问者，一个是元素层次结构，提供了抽象元素和具体元素。相同的访问者可以以不同的方式访问不同的元素，相同的元素可以接受不同访问者以不同访问方式访问。
* 在访问者模式中，增加新的访问者无须修改原有系统，系统具有较好的可扩展性。

### 小结

访问者模式的主要优点在于使得增加新的访问操作变得很容易，将有关元素对象的访问行为集中到一个访问者对象中，而不是分散到一个个的元素类中，还可以跨过类的等级结构访问属于不同的等级结构的元素类，让用户能够在不修改现有类层次结构的情况下，定义该类层次结构的操作；其主要缺点在于增加新的元素类很困难，而且在一定程度上破坏系统的封装性。

### WhileX 语言（理解）


