package handler.impl;

import handler.HttpHandler;
import invocation.Invocation;
import registry.impl.LocalRegistry;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class HttpHelloServiceHandler implements HttpHandler {

    public void handle(ServletRequest req, ServletResponse res) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(req.getInputStream());
        Invocation invocation = null;
        try {
            invocation = (Invocation) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (Objects.isNull(invocation)) {
            throw new NullPointerException();
        }
        String interfaceName = invocation.getInterfaceName();
        String interfaceImpl = LocalRegistry.getService(interfaceName);
        String funcName = invocation.getMethodName();
        Class[] paramType = invocation.getParamType();
        Object[] params = invocation.getParams();
        try {
            Object obj = Class.forName(interfaceImpl).newInstance();
            Method method = obj.getClass().getDeclaredMethod(funcName, paramType);
            String result = (String) method.invoke(obj, params);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(res.getOutputStream()));
            bw.write(result);
            bw.flush();
            bw.close();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
