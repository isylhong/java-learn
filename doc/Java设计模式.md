## Java设计模式

- 创建型：
简单工厂模式、抽象工厂模式、单例模式、多例模式、构建者模式。

- 结构型：
适配器、装饰器、代理模式、桥接模式、外观模式。

**代理、桥接、装饰器、适配器 4 种设计模式的区别:**  
代理、桥接、装饰器、适配器，这 4 种模式是比较常用的结构型设计模式。它们的代码结构非常相似。笼统来说，它们都可以称为 Wrapper 模式，也就是通过 Wrapper 类二次封装原始类。
> 代理模式：代理模式在不改变原始类接口的条件下，为原始类定义一个代理类，主要目的是控制访问，而非加强功能，这是它跟装饰器模式最大的不同。  
> 
> 装饰器模式：装饰者模式在不改变原始类接口的情况下，对原始类功能进行增强，并且支持多个装饰器的嵌套使用。  
>
> 适配器模式：适配器模式是一种事后的补救策略。适配器提供跟原始类不同的接口，而代理模式、装饰器模式提供的都是跟原始类相同的接口。  
>
> 桥接模式：桥接模式的目的是将接口部分和实现部分分离，从而让它们可以较为容易、也相对独立地加以改变。桥接模式通过组合关系来替代继承关系，避免继承层次的指数级爆炸。

- 行为型：
策略模式、责任链模式、观察者模式、访问者模式。


## 1 工厂方法模式
### 1.1 工厂方法模式-案例1
AOP获取AopProxy，进一步获取目标代理类
| 模块 | spring-aop |
| ---- | ---------- |
| 客户类(调用工厂) | org.springframework.aop.framework.ProxyFactory |
| 工厂接口 | org.springframework.aop.framework.AopProxyFactory |
| 工厂实现类 | org.springframework.aop.framework.DefaultAopProxyFactory |
| 产品接口 | org.springframework.aop.framework.AopProxy |
| 产品接口实现类 | org.springframework.aop.framework.JdkDynamicAopProxy、  org.springframework.aop.framework.ObjenesisCglibAopProxy |

### 1.2 工厂方法模式-案例2
slf4j获取Logger实例
| 模块 | slf4j-api |
| ---- | ---------- |
| 客户类(调用工厂) | org.slf4j.LoggerFactory |
| 工厂接口 | org.slf4j.ILoggerFactory |
| 工厂实现类 | org.apache.logging.slf4j.Log4jLoggerFactory、  ch.qos.logback.classic.LoggerContext |
| 产品接口 | org.slf4j.Logger |
| 产品接口实现类 | org.apache.logging.slf4j.Log4jLogger、  ch.qos.logback.classic.Logger |

### 1.3 工厂方法模式-案例3
mybatis获取Transaction实例
| 模块 | slf4j-api |
| ---- | ---------- |
| 客户类(调用工厂) | - |
| 工厂接口 | org.apache.ibatis.transaction.TransactionFactory |
| 工厂实现类 | org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory、  org.apache.ibatis.transaction.managed.ManagedTransactionFactory、  org.mybatis.spring.transaction.SpringManagedTransactionFactory |
| 产品接口 | org.apache.ibatis.transaction.Transaction |
| 产品接口实现类 | org.apache.ibatis.transaction.jdbc.JdbcTransaction、  org.apache.ibatis.transaction.managed.ManagedTransaction、  org.mybatis.spring.transaction.SpringManagedTransaction |


## 2 责任链模式
### 2.1 责任链模式-案例1
AOP执行链
| 模块 | Spring-Aop |
| ---- | ---------- |
| 链管理类 | org.springframework.aop.framework.ReflectiveMethodInvocation |
| 链节点类型 | org.springframework.aop.framework.InterceptorAndDynamicMethodMatcher、  org.aopalliance.intercept.MethodInterceptor |
| 链节点 | org.springframework.transaction.interceptor.TransactionInterceptor、  org.springframework.aop.aspectj.AspectJAroundAdvice、  org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor、  org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor、  …… |


# 3 构建者模式
特点：
- (1) 目标类不设置setter方法。
- (2) 构建类放在与目标类同一个包内（或构建类直接放在目标中），然后通过点(.)运算符给目标类属性赋值。

关于Builder模式，我们一定要分清和模板方法的区别，其实就是到底谁承担了"监工"的责任，
在模板方法中父类承担了这个责任，而在Builder中，有另外一个专门的类来完成这样的操作，
这样做的好处是类的隔离，比如说在Main中，用户根本就不知道有Builder这个抽象类，
同样的Director这个监工的根本就不管到底是哪一个实现类，因为任何一个都会被转换为父类，
然后进行处理（面向抽象编程的思想），因此很好的实现了隔离，同样的这样设计的好处是复用了，
隔离的越好复用起来就越方便，我们完全可以思考，假如还有另外一个监工，使用了不同的construct方法来组装
那么对于原来的代码我们不用做任何的修改，只用增加这样的一个监工类，然后定义好相应的方法就好了，
之后再Main中使用，这样的一种思想使得我们不用修改源代码，复用（Builder以及其子类）就很方便了，
同样的，如果想增加一个新的Builder的子类，只要照着父类的方法进行填充，再加上自己的方法就好了，
完全不用修改代码，这也是一种复用，因此这种复用（组件）的思想在设计模式中随处可见，
本质就是高内聚低耦合，组件开发，尽量不修改原来的代码，有可扩展性，理解了这一点，我们再看看模板方法，责任全放
如果责任需要改变，则必须要修改父类中的责任方法了，这样就修改了原来的代码，不利于复用，这也是两者的本质区别。


### 3.1 构建者模式-案例1
mybatis环境类Environment的创建
| 模块 | Mybatis |
| ---- | ------- |
| 目标类 | org.apache.ibatis.mapping.Environment |
| 构建类 | org.apache.ibatis.mapping.Environment.Builder |


## 4 适配器模式
分两种：
- 类适配器
- 对象适配器

适用场景：
- 重构第三方登录自由适配场景

### 4.1 对象适配器模式1
#### 4.1.1 对象适配器模式-案例1
log4j适配到slf4j
| 模块 | Log4j |
| ---- | ------- |
| 目标接口 | org.slf4j.spi.LocationAwareLogger |
| 现有类 | org.apache.log4j.Logger |
| 适配器类 | org.slf4j.impl.Log4jLoggerAdapter |

### 4.2 类适配器模式
#### 4.2.1 类适配器模式-案例1
log4j2适配到slf4j
| 模块 | Log4j |
| ---- | ------- |
| 目标接口 | org.slf4j.spi.LocationAwareLogger |
| 现有类 | org.apache.log4j.Logger |
| 适配器类 | org.slf4j.impl.Log4jLoggerAdapter |


## 5 装饰器模式
装饰器模式又称为包装（Wrapper）模式。装饰器模式以多客户端透明的方式扩展对象的功能，是继承关系的一个替代方案。

装饰器模式的结构:  
通常给对象添加功能，要么直接修改对象添加相应的功能，要么派生子类来扩展，抑或是使用对象组合的方式。显然，直接修改对应的类的方式并不可取，在面向对象的设计中，我们应该尽量使用组合对象而不是继承对象来扩展和复用功能，装饰器模式就是基于对象组合的方式的。  
装饰器模式以对客户端透明的方式动态地给一个对象附加上了更多的责任。换言之，客户端并不会角色对象在装饰前和装饰后有什么不同。装饰器模式可以在不用创建更多子类的情况下，将对象的功能加以扩展。

装饰器模式中的角色有：
- (1) 抽象构件角色
  给出一个抽象接口，以规范准备接受附加责任的对象
- (2) 具体构件角色
  定义一个将要接受附加责任的类
- (3) 装饰角色
  持有一个构建对象的实例，并定义一个与抽象构件接口一致的接口
- (4) 具体装饰角色
  负责给构建对象贴上附加的责任

### 5.1 装饰器模式模式-案例1
mybatis缓存Cache使用装饰器模式
| 模块 | Mybatis |
| ---- | ------- |
| 目标接口 | org.apache.ibatis.cache.Cache |
| 装饰器类 | org.apache.ibatis.cache.decorators.LoggingCache、  org.apache.ibatis.cache.decorators.SynchronizedCache、  org.apache.ibatis.cache.decorators.BlockingCache、  ... |


## 6 观察者模式
具体案例可查看JDK中的应用
java.util.Observer（观察者）
java.util.Observable（主题）


## 7 代理模式
代理模式的定义很简单：  
给某一对象提供一个代理对象，并由代理对象控制对原对象的引用。

代理模式的结构:  
有些情况下，一个客户不想或者不能够直接引用一个对象，可以通过代理对象在客户端和目标对象之间起到中介作用。  
代理模式中的角色有：
- (1) 抽象对象角色  
  声明了目标对象和代理对象的共同接口，这样一来在任何可以使用目标对象的地方都可以使用代理对象
- (2) 目标对象角色  
  定义了代理对象所代表的目标对象
- (3) 代理对象角色  
  代理对象内部含有目标对象的引用，从而可以在任何时候操作目标对象；代理对象提供一个与目标对象相同的接口，以便可以在任何时候替代目标对象


### 7.1 静态代理
示例：  
这里模拟的是作为访问网站的场景，以新浪网举例。  
我们通常访问新浪网，几乎所有的Web项目尤其是新浪这种大型网站，是不可能采用集中式的架构的，使用的一定是分布式的架构。
分布式架构对于用户来说，我们发起链接的时候，链接指向的并不是最终的应用服务器，而是代理服务器比如Nginx，用以做负载均衡。
所以，我们的例子，简化来说就是用户访问新浪网-->代理服务器-->最终服务器

静态代理的缺点：  
静态代理的特点是静态代理的代理类是程序员创建的，在程序运行之前静态代理的.class文件已经存在了。
从静态代理模式的代码来看，静态代理模式确实有一个代理对象来控制实际对象的引用，并通过代理对象来使用实际对象。这种模式在代理量较小的时候还可以，但是代理量一大起来，就存在着两个比较大的缺点：

- 静态代理的内容，即NginxProxy的路由选择这几行代码，只能服务于Server接口而不能服务于其他接口，如果其它接口想用这几行代码，比如新增一个静态代理类。久而久之，由于静态代理的内容无法复用，必然造成静态代理类的不断庞大
- Server接口里面如果新增了一个方法，比如getPageData(String url)方法，实际对象实现了这个方法，代理对象也必须新增方法getPageData(String url)，去给getPageData(String url)增加代理内容（假如需要的话）


### 7.2 动态代理
动态代理的优点：
- 最直观的，类少了很多
- 代理内容也就是InvocationHandler接口的实现类可以复用，可以给A接口用、也可以给B接口用，A接口用了InvocationHandler接口实现类A的代理，不想用了，可以方便地换成InvocationHandler接口实现B的代理
- 最重要的，用了动态代理，就可以在不修改原来代码的基础上，就在原来代码的基础上做操作，这就是AOP即面向切面编程
