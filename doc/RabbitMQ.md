# Rabbit MQ

## 1 Windows平台安装Rabbit MQ
**相关软件下载链接：**  
Erlang和Rabbit MQ版本对应：[链接](https://www.rabbitmq.com/which-erlang.html)  
Erlang下载链接：[链接](https://github.com/erlang/otp)  
Rabbit MQ下载链接：[链接](https://github.com/rabbitmq/rabbitmq-server/releases)

### 1.1 下载Erlang，并安装
安装完成后进行如下配置：
- (1) 为Erl配置环境变量：ERLANG_HOME=${ERLANG安装目录}
- (2) Path变量新建项：$ERLANG_HOME$\bin

### 1.2 下载Rabbit MQ，并安装
注意：Erlang和Rabbit MQ有强烈的版本对应关系。
版本不对应，将导致Rabbit MQ启动失败，报错如下错误 Error: {:unable_to_load_rabbit, {‘no such file or directory’, ‘rabbit.app’}}

### 1.3 启动Web管理插件
进入${RabbitMQ安装根目录}\sbin\目录下，打开CMD，执行以下命令:
```shell
rabbitmq-plugins.bat enable rabbitmq_management
```

### 1.4 启动Rabbit MQ
```shell
# 访问地址：localhost:15672
# 默认用户名：guest
# 默认密码：guest
rabbitmq-plugins.bat start
```

###   1.5 其他常用命令
| 操作 | 命令 |
| - | ---- |
| 查看所有的用户信息 | rabbitmqctl.bat list_users |
| 新增用户 | rabbitmqctl.bat add_user ${username} ${password} |
| 为用户添加所有权限 | rabbitmqctl.bat set_permissions ${username} ".*" ".*" ".*" |
| 修改用户密码 |　rabbitmqctl.bat change_password ${username} ${new_password} |
