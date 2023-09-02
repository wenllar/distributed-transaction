package reflect;

import httpClient.HttpClient;
import invocation.Invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PorxyFactory<T> {

    public static <T> T getProxy(final Class interfaceClass) {
        Object proxyInstance = Proxy.newProxyInstance(PorxyFactory.class.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Invocation invocation = Invocation.builder()
                        .interfaceName(interfaceClass.getName())
                        .methodName(method.getName())
                        .paramType(method.getParameterTypes())
                        .params(args).build();
                String result = HttpClient.newInstance().send(invocation);
                return result;
            }
        });
        return (T) proxyInstance;
    }


    public static <T> T getProxyAndSetHeader(final Class interfaceClass) {
        Object proxyInstance = Proxy.newProxyInstance(PorxyFactory.class.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Invocation invocation = Invocation.builder()
                        .interfaceName(interfaceClass.getName())
                        .methodName(method.getName())
                        .paramType(method.getParameterTypes())
                        .params(args).build();
                String result = HttpClient.newInstance().sendWithHeader(invocation);
                return result;
            }
        });
        return (T) proxyInstance;
    }

    public static <T> T getProxy(final Class interfaceClass, String host, int port) {
        System.out.println(String.format("发起dubbo调用请求：Host: %s, port:%s", host, port));
        Object proxyInstance = Proxy.newProxyInstance(PorxyFactory.class.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Invocation invocation = Invocation.builder()
                        .interfaceName(interfaceClass.getName())
                        .methodName(method.getName())
                        .paramType(method.getParameterTypes())
                        .params(args).build();
                String result = HttpClient.newInstance().send(host, port, invocation);
                return result;
            }
        });
        return (T) proxyInstance;
    }
}
