package servlet;

import handler.impl.HttpHelloServiceHandler;
import lombok.SneakyThrows;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JettyHandler extends AbstractHandler {
    @SneakyThrows
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        System.out.println("Jetty Handler 请求来了。。。。。");
        System.out.println(String.format("target:%s", target));
        System.out.println(baseRequest.toString());
        System.out.println(httpServletRequest.toString());
        System.out.println(httpServletResponse.toString());;
        new HttpHelloServiceHandler().handle(baseRequest, httpServletResponse);
        baseRequest.setHandled(true);
    }
}
