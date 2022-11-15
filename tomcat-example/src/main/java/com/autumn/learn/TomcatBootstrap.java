package com.autumn.learn;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;

public class TomcatBootstrap {
    public static void main(String[] args) throws IOException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8888);

        // tomcat9.0以后的版本必须手动调用getConnector()才会开始监听端口
        tomcat.getConnector();

        String webappsPath = "tomcat-example/webapps";
        String appBase = new File(webappsPath).getCanonicalPath();
        Host host = tomcat.getHost();
        host.setAppBase(appBase);
        if (host instanceof StandardHost) {
            ((StandardHost) host).setUnpackWARs(true);
        }

        String contextPath1 = "/tmt";
        String webappPath1 = "tmt";
        String docBase1 = new File(webappsPath, webappPath1).getCanonicalPath();
        System.out.println(docBase1);
        Context context1 = tomcat.addWebapp(contextPath1, docBase1);

//        String contextPath2 = "/mvc";
//        String webappPath2 = "/mvc";
//        tomcat.addWebapp(contextPath2, new File(webappsPath, webappPath2).getCanonicalPath());


        String contextPath3 = "/springmvc";
        String webappPath3 = "/springmvc";
        StandardContext ctx = new StandardContext();
        ctx.setPath(contextPath3);
        ctx.setDocBase(webappPath3);
        ctx.addLifecycleListener(new Tomcat.DefaultWebXmlListener());
        ctx.addLifecycleListener(new ContextConfig());
        tomcat.getHost().addChild(ctx);

//        tomcat.addWebapp(contextPath3,new File(webappsPath, webappPath3).getCanonicalPath());

        try {
            tomcat.start();
            System.out.println("tomcat启动成功...");
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            System.out.println("tomcat启动失败!!!");
        }
    }
}
