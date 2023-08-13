package server.impl;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import server.HttpServer;
import servlet.DispatcherServlet;

public class HttpTomcatServer implements HttpServer {

    @Override
    public void startServer(String host, int port) {
        this.startTomcat(host, port);
    }

    /**
     * 图灵课堂学到的
     * @throws LifecycleException
     */
    private void startTomcat(String ip, int port) {
        Tomcat tomcat = new Tomcat();

        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");

        Connector connector = new Connector();
        connector.setPort(port);

        Engine engine = new StandardEngine();
        engine.setDefaultHost(ip);

        Host host = new StandardHost();
        host.setName(ip);

        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        host.addChild(context);
        engine.addChild(host);

        service.setContainer(engine);
        service.addConnector(connector);

        tomcat.addServlet(contextPath, "dispacther", new DispatcherServlet());
        context.addServletMappingDecoded("/hello", "dispacther");

        try {
            System.out.println("启动Tomcat服务器");
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }

    }

    /**
     * 网上找到的启动Tomcat的方式
     */
    private void startTomcatInAnatherWay(){
        //构建tomcat对象,此对象为启动tomcat服务的入口对象
        Tomcat tomcat = new Tomcat();
        //构建Connector对象,此对象负责与客户端的连接.
        Connector con = new Connector("HTTP/1.1");
        //设置服务端的监听端口
        con.setPort(8080);
        //将Connector注册到service中
        tomcat.getService().addConnector(con);
        //注册servlet
        Context ctx = tomcat.addContext("/", null);
        Tomcat.addServlet(ctx, "dispacther", new DispatcherServlet());
        //Tomcat.addServlet(ctx,"dispacther","server.HttpServerlet");

        //映射servlet
        ctx.addServletMappingDecoded("/hello", "dispacther");
        //启动tomcat
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        //阻塞当前线程
        System.out.println(Thread.currentThread().getName());
        tomcat.getServer().await();

    }
}
