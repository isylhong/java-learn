## URI、URL详解
eg:   
file://root:123456@example.com:80/data?key=value&key2=value2#fragid1

### 1、URI 格式  
```
uri: <scheme>://<userinfo>@<host>:<port><path>?<query>#<fragment>  
<schemeSpecificPart> = //<userinfo>@<host>:<port><path>?<query>
<authority> = <userinfo>@<host>:<port>
<userinfo> = <username>:<password>
```

### 1、URL 格式
~~~
url: <protocol>://<userinfo>@<host>:<port><path>?<query>#<ref>  
<authority> = <userinfo>@<host>:<port>
<file> = <path>?<query>
~~~
