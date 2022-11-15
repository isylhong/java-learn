package com.autumn.learn.mybatis;

import com.autumn.learn.mybatis.dao.UserDao;
import com.autumn.learn.mybatis.domain.User;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.StringTypeHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author yl
 * @since 2022-11-14 22:19
 */
public class MybatisTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisTests.class);

    @Test
    public void testMybatisByCode() {
        Configuration configuration = new Configuration();
        configuration.setLogImpl(Log4jImpl.class); // 设置使用Log4j日志框架

        // 1、配置数据源属性配置。为了方便，这里直接硬编码。也可写入配置文件，然后加载。
        Properties properties = new Properties();
        properties.setProperty("driver", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("url", "jdbc:mysql://localhost:3306/study_mybatis?useUnicode=true&characterEncoding" +
                "=utf8&serverTimezone=UTC");
        properties.setProperty("username", "root");
        properties.setProperty("password", "123456");

        // 2、创建数据源工厂对象，通过工厂对象创建DataSource
        DataSourceFactory dsf = new PooledDataSourceFactory();
        dsf.setProperties(properties);
        DataSource dataSource = dsf.getDataSource();

        // 3、创建事务工厂类
        JdbcTransactionFactory jtf = new JdbcTransactionFactory();

        // 4、创建设置Environment对象
        Environment.Builder builder = new Environment.Builder("mysql"); // 自定义id
        Environment environment = builder.dataSource(dataSource).transactionFactory(jtf).build();
        configuration.setEnvironment(environment);

        // 5. 解析XXXMapper.xml配置文件，有两种方式
        //    方法一： 在代码中通过XMLMapperBuilder对象，指定解析某个XXXMapper.xml映射文件
        //    方法二： 在mybatis全局配置文件的<mappers><mapper/></mappers>标签里指定映射文件
        URL url = getClass().getResource("./mapper/UserMapper.xml"); // 方法一：在代码中指定解析某个配置文件
        assert url != null;
        XMLMapperBuilder xmlMapperBuilder = null;
        try {
            xmlMapperBuilder = new XMLMapperBuilder(url.openStream(), configuration,
                    url.getPath(), configuration.getSqlFragments());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert xmlMapperBuilder != null;
        xmlMapperBuilder.parse(); // 执行Mapper映射文件解析

        // 6. 构建者模式：创建一个SqlSessionFactory对象
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = factoryBuilder.build(configuration);

        // 7. 抽象工厂模式：通过SqlSessionFactory创建一个SqlSession实现对象
        SqlSession sqlSession = sqlSessionFactory.openSession(false);

        // 8. 获取XXXDao接口代理对象，执行相关操作
        UserDao userMapper = sqlSession.getMapper(UserDao.class);
        userMapper.addUser(new User("202207", "tom"));

        // 9. 提交事务，参数决定是否强制提交事务。
        // 强制提交事务是session一定会提交事务。非强制提交事务则会根据当前操作是否更新数据库中数据来决定session是否提交事务。
        sqlSession.commit(true);
    }

    @Test
    public void testMybatisByConfigFile() throws IOException {
        // 1. 读取mybatis配置文件
        InputStream cis = this.getClass().getResourceAsStream("mybatis-config.xml");
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(cis);

        // 2. 解析配置文件，获取全局配置对象
        Configuration configuration = xmlConfigBuilder.parse();

        // 3. 解析XXXMapper.xml配置文件，有两种方式
        //    方法一： 在代码中通过XMLMapperBuilder对象，指定解析某个XXXMapper.xml映射文件
        //    方法二： 在mybatis全局配置文件的<mappers><mapper/></mappers>标签里指定映射文件
        // 这里直接通过方法二在配置文件中配置

        // 4. 构建者模式：创建SqlSessionFactory构建者对象
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        // 通过构建者对象创建一个SqlSessionFactory实例对象
        SqlSessionFactory sqlSessionFactory = factoryBuilder.build(configuration);

        // 5. 抽象工厂模式：通过SqlSessionFactory创建一个SqlSession实现对象
        SqlSession sqlSession = sqlSessionFactory.openSession(false);

        // 6. 获取XXXDao接口代理对象，执行相关操作
        UserDao userMapper = sqlSession.getMapper(UserDao.class);
        userMapper.addUser(new User("202206", "lucy"));

        // 7. 提交事务，参数决定是否强制提交事务。
        // 强制提交事务是一定会提交事务。非强制提交事务则会根据当前操作是否更新数据库中数据来决定是否提交事务。
        sqlSession.commit(true);
    }

    @Test
    public void test01() {
        Type rawType = new StringTypeHandler().getRawType();
        LOGGER.info(rawType.toString());
    }
}
