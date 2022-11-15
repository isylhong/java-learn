# Spring框架

## 1 Spring Bean生命周期

### 1.1 Bean初始化方法执行顺序
构造函数 -> @PostConstruct -> InitializingBean.afterPropertiesSet() -> xml配置(init-method)

### 1.2 Bean销毁方法执行顺序
@PreDestroy -> DisposableBean.destroy() -> xml配置(destroy-method)