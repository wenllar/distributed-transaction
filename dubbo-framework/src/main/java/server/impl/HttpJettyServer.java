package server.impl;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import server.HttpServer;
import servlet.DispatcherServlet;

public class HttpJettyServer implements HttpServer {
    @Override
    public void startServer(String host, int port) throws Exception{
        System.out.println("启动Jetty服务器");
        this.startJettyServer(host, port);
    }

    private void startJettyServer(String host, int port) throws Exception {
        // 服务器的监听端口
        Server server = new Server(port);
        //ServletHandler通过一个servlet创建了一个非常简单的context处理器
        //这个处理器需要在Server上注册
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        //传入能匹配到这个servlet的路径
        //提示：这是一个未经处理的servlet，没有通过web.xml或@WebServlet注解或其他方式配置
        handler.addServletWithMapping(DispatcherServlet.class, "/*");
        server.start();
        server.join();
    }

    private void startJettyServerWithHandler(String host, int port) throws Exception {
        // 服务器的监听端口
        Server server = new Server(port);

        // Add a single handler on context "/hello"
        ServletContextHandler context = new ServletContextHandler ();
        //context.setContextPath("/hello");
        //context.setHandler(new JettyHandler()); // 这种方式不行，一定要Servlet
        context.addServlet(DispatcherServlet.class, "/hello");

        // Can be accessed using http://localhost:8080/hello
        server.setHandler(context);

        // Start the server
        server.start();
        server.join();
    }
}
