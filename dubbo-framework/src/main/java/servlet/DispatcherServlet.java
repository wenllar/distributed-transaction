package servlet;

import handler.impl.HttpHelloServiceHandler;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet{

    @SneakyThrows
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("Servlet 请求来了。。。。。");
         new HttpHelloServiceHandler().handle(req, res);
    }
}
