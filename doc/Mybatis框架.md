# Mybatis框架

## 1 原生mybatis执行流程
1. 解析mybatis配置文件，调用SqlSessionFactoryBuilder().build()方法生成一个DefaultSqlSessionFactory对象。
    - 1.1 通过XmlConfigBuilder创建一个Configuration对象。
    - 1.2 通过XmlConfigBuilder.parse()读取mybatis配置文件。
    - 1.3 通过XmlMapperBuilder.parse()读取并解析Mapper文件。
        - 1.3.1 调用configurationElement(this.parser.evalNode("/mapper"));解析Mapper文件中的各种元素节点，如/mapper/resultMap、/mapper/sql、select|insert|update|delete节点。解析生成的内容都会存入Configuration对象中，如保存每条sql语句对应的MappedStatement对象。
        - 1.3.2 调用bindMapperForNamespace();里面会继续调用Configuration.addMapper(boundType)为每个配置文件生成一个对应的MapperProxyFactory对象，并注册到Configuration中mapperRegistry变量中。
    - 1.4 SqlSessionFactoryBuilder().build(configuration);创建一个DefaultSqlSessionFactory对象。

2. 调用DefaultSqlSessionFactory#openSession()方法获取到一个DefaultSqlSession对象。

3. 调用SqlSession#getMapper()方法获取到一个Dao接口的代理对象。

4. Dao接口函数调用，最终执行代理对象MapperProxy#invoke()方法。
    - 4.1 MapperProxy#invoke()中首先创建一个MapperMethod对象，并注入到MapperProxy.PlainMethodInvoker中生成一个MapperProxy.PlainMethodInvoker对象。
    - 4.2 调用org.apache.ibatis.binding.MapperProxy.PlainMethodInvoker#invoke()方法，该方法体中继续调用this.mapperMethod.execute(sqlSession, args);
    - 4.3 MapperMethod#execute()中首先会对传入的参数进行包装，然后根据sql类型选择调用传入的sqlSession对象的insert|delete|update|select方法。
      如果传入的是sqlSession是SqlSessionTemplate，则会再生成一个sqlSession代理对象，进一步执行org.mybatis.spring.SqlSessionTemplate.SqlSessionInterceptor#invoke()方法。
        - (1) 调用SqlSessionUtils.getSqlSession()获取一个DefaultSqlSession对象。实际调用传入的sessionFactory的openSession(executorType)方法，而openSession(executorType)最终调用的是DefaultSqlSessionFactory#openSessionFromDataSource()方法。
        - (2) 通过反射执行获取到的DefaultSqlSession对象的增删改查方法。

5. DefaultSqlSession增删改查方法的执行
    - 5.1 通过this.configuration.getMappedStatement(statement)获取到一个MappedStatement对象;
    - 5.2 调用DefaultSqlSession中的Executor实例对象(CachingExecutor)的update、query等方法完成增删改查。

6. 通过传入参数MappedStatement的getBoundSql()获取到一个boundSql对象。该方法中实现了sql语句的动态化参数解析。
   mybatis动态化参数解析：
   入口：`org.apache.ibatis.scripting.xmltags.DynamicSqlSource#getBoundSql`
    - (1) 将Mapper文件中select|insert|delete|update节点内的<where>、<if>、<include>等节点替换为相应的文本内容。
    - (2) 创建一个SqlSourceBuilder对象。
    - (3) 调用SqlSourceBuilder对象的parse()方法。该方法中创建了ParameterMappingTokenHandler和GenericTokenParser两个对象。
        - GenericTokenParser.parse(): 将sql语句中的所有#{}|\${}参数占位符替换为？。
        - ParameterMappingTokenHandler.handleToken():为每一个参数占位符#{}|\${}生成一个ParameterMapping对象，并存入到这个对象内的List集合中。ParameterMapping对象用于记录参数的javaType、jdbcType、typeHandler、resultMap等信息。
    - (4) 返回一个StaticSqlSource对象。该对象包含了第3步中将#{}|${}替换为?后的sql语句，及第3步中为参数占位符生成的ParameterMapping列表。
    - (5) 调用StaticSqlSource的getBoundSqlgetBoundSql()方法返回一个BoundSql对象。这个对象最后传入作为StatementHandler的属性，并在StatementHandler#parameterize()方法中使用具体的参数值替换掉sql语句中的?占位符。

7. CachingExecutororg.apache.ibatis.executor.CachingExecutor#query()方法
    - 7.1 先从二级缓存中获取结果集，如果存在则直接返回，不存在则再从一级缓存中获取。一级缓存中如果存在，则直接返回，不存在则从数据库中查询。
        - 二级缓存获取内容（命名空间级别，多个connection间共享）。首先通过事务缓存管理器TransactionalCacheManager获取到当前查询方法所属命名空间对应的事务缓存对象TransactionalCache，然后再通过事务缓存对象获取到与当前方法对应的缓存。
        - 一级缓存（connection级别，单个connection独享）获取内容。

8. 数据库中查询获取数据。
    - 8.1 通过传入的MappedStatement获取到全局Configuration对象。
    - 8.2 通过Configuration的newStatementHandler()方法获取一个StatementHandler对象。
    - 8.3 调用SimpleExecutor#prepareStatement()方法获取一个Statement对象。
        - 8.3.1 通过transaction对象获取一个connection连接。
        - 8.3.2 通过获取到的connection获取到一个PreparedStatement对象。
        - 8.3.3 使用具体的参数值替换掉PreparedStatement对象sql语句中的?占位符。
    - 8.4 调用StatementHandler对象的update、query方法。

9. 执行StatementHandler对象的query方法。
    - 9.1 调用的是Statement对象的execute方法通过JDBC完成SQL执行。
    - 9.2 调用ResultSetHandler.handleResultSets()方法处理结果集。


## ２ mybatis-spring
MapperFactoryBean(实现了FactoryBean)，该对象在getObject()中借助SqlSessionTemplate的Configuration的MapperResigty获取到一个和Dao接口关联的MapperProxyFactory对象，
然后通过获取到的MapperProxyFactory对象创建一个Dao接口的代理MapperProxy（该代理实现了InvocationHandler接口）。

SessionHolder包含sqlSession，sqlSession包含transaction，transaction包含connection。  
**事务传播方法执行链**：   
外围方法的代理调用 -》 匹配事务方法 -》 检索符合方法的拦截器 -》 创建代理方法调用链 -》 执行事务拦截器TransactionInterceptor -》获取事务管理器 -》 创建事务对象
-》判断事务对象中是否存在Connection，不存在则通过数据源创建新的connection -》 修改事务为手动提交 -》 将connection绑定到当前线程 -》 初始化事务同步 -》 代理调用下一个内嵌的事务方法。

1. 通过org.mybatis.spring.SqlSessionFactoryBean完成一些基本初始化。如：mybatis配置文件的解析、Mapper配置文件解析、spring事务对象引入、DefaultSqlSessionFactory对象创建等。
2. org.mybatis.spring.mapper.MapperScannerConfigurer中使用org.mybatis.spring.mapper.ClassPathMapperScanner扫描Dao接口所在包，生成BeanDefination。
    - 2.1 扫描完成后，遍历生成的BeanDefination，修改BeanDefination中的部分属性值。
        - 2.1.1 修改BeanDefination中beanClass属性值为org.mybatis.spring.mapper.MapperFactoryBean。用于在实例化时创建MapperFactoryBean对象。
        - 2.1.2 为BeanDefination中的PropertyValues添加属性("sqlSessionFactory", new RuntimeBeanReference(this.sqlSessionFactoryBeanName))。用于在实例化时注入DefaultSqlSessionFactory实例。

3. 为Service类注入的Dao接口实现类。
    - 3.1 通过扫描到的BeanDefination为每个Dao接口实例化一个MapperFactoryBean对象。由于MapperFactoryBean实现里FactoryBean<T>接口。因此为Service类注入的Dao接口实现类为MapperFactoryBean.getObject()返回的对象。
    - 3.2 实例化MapperFactoryBean对象时会在其父类org.mybatis.spring.support.SqlSessionDaoSupport中注入DefaultSqlSessionFactory。并为sqlSession属性创建一个org.mybatis.spring.SqlSessionTemplate对象。
    - 4.1 MapperFactoryBean调用this.getSqlSession().getMapper(this.mapperInterface);获取Mapper对象。即，通过其中的SqlSessionTemplate属性获取到一个MapperProxy对象。
    - 4.2 SqlSessionTemplate#getMapper()实际调用的是this.getConfiguration().getMapper(type, this);
    - 4.3 org.apache.ibatis.session.Configuration#getMapper实际调用的是this.mapperRegistry.getMapper(type, sqlSession);
    - 4.4 org.apache.ibatis.binding.MapperRegistry#getMapper()中首先获取到之前注册的MapperProxyFactory对象，然后通过MapperProxyFactory.newInstance(sqlSession)创建一个包含MapperProxy对象的代理对象，注入到Service类中。
