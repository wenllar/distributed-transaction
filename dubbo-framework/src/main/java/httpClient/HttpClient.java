package httpClient;

import invocation.Invocation;
import loadBalance.LoadBalance;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpClient {

    /**
     * 双重检测
     */
    private static volatile HttpClient httpClient = null;

    private HttpClient() {
    }

    public static HttpClient newInstance() {
        if (null == httpClient) {
            synchronized (HttpClient.class) {
                if (null == httpClient) {
                    httpClient = new HttpClient();
                }
            }
        }
        return httpClient;
    }

    public String send(Invocation invocation) throws IOException, ClassNotFoundException {
        registry.URL remoteUrl = getOneUrlFromRemoteRegistry(invocation.getInterfaceName());

        System.out.println(String.format("Host: %s, port:%s", remoteUrl.getHost(), remoteUrl.getPort()));
        URL url = new URL("http", remoteUrl.getHost(), remoteUrl.getPort(), "/hello");
        return this.sendHttpRequest(url, invocation);
    }

    public String send(String host, int port, Invocation invocation) throws IOException, ClassNotFoundException {
        URL url = new URL("http", host, port, "/hello");
        return this.sendHttpRequest(url, invocation);

    }

    private String sendHttpRequest(URL url, Invocation invocation) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setUseCaches(false);
        // 请求超时5秒
        conn.setConnectTimeout(5000);
        //发送Post请求
        conn.setDoOutput(true);
        ObjectOutputStream wr = new ObjectOutputStream(conn.getOutputStream());
        wr.writeObject(invocation);
        wr.flush();
        wr.close();
        String inLine = "";
        StringBuilder resultBd = new StringBuilder();
        BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while (null != (inLine = bf.readLine())) {
            resultBd.append(inLine);
        }
        return resultBd.toString();
    }

    /**
     * 获取注册中心所有服务提供者的实例
     * @param interfaceName
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public List<registry.URL> getAllUrlsFromRemoteRegistry(String interfaceName) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("D:\\myfile.txt")));
        Map<String, List<registry.URL>> remoteRegistry = (Map<String, List<registry.URL>>) objectInputStream.readObject();
        return remoteRegistry.get(interfaceName);
    }

    /**
     * 获取一个负载均衡后的服务提供者的实例
     * @param interfaceName
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public registry.URL getOneUrlFromRemoteRegistry(String interfaceName) throws IOException, ClassNotFoundException {
        Map<String, List<registry.URL>> remoteRegistry;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("D:\\myfile.txt")))) {
            remoteRegistry = (Map<String, List<registry.URL>>) objectInputStream.readObject();
        }
        List<registry.URL> urls = remoteRegistry.get(interfaceName);
        if (urls == null || urls.isEmpty()) {
            throw new IllegalStateException();
        }
        registry.URL url = LoadBalance.loadBalance(urls) ;
        return url;
    }
}
