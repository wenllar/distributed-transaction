package registry.impl;

import registry.URL;

import java.io.*;
import java.util.*;

public class RemoteRegistry {//implements Registry

    static Map<String, List<URL>> REGISTRY_MAP = new HashMap<>();

    private RemoteRegistry(){

    }
    public static boolean regist(String interfaceName, URL url) throws IOException, ClassNotFoundException {
        if(Objects.isNull(interfaceName) || Objects.isNull(interfaceName)){
            throw new NullPointerException();
        }
        if(new File("D:\\myfile.txt").exists()){
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File("D:\\myfile.txt")));
            REGISTRY_MAP = (Map<String, List<URL>>) objectInputStream.readObject();
        }
        List<URL> serviceProviderUrls = REGISTRY_MAP.get(interfaceName);
        if(null == serviceProviderUrls){
            serviceProviderUrls = new ArrayList<>(16);
        }
        serviceProviderUrls.add(url);
        REGISTRY_MAP.put(interfaceName, serviceProviderUrls);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(new File("D:\\myfile.txt")));
        objectOutputStream.writeObject(REGISTRY_MAP);
        objectOutputStream.flush();
        objectOutputStream.close();
        return true;
    }

    public static boolean unRegist(String interfaceName){
        if(Objects.isNull(interfaceName) || Objects.isNull(interfaceName)){
            throw new NullPointerException();
        }
       return null == REGISTRY_MAP.remove(interfaceName);
    }

    public static List<URL> getService(String interfaceName) {
        return REGISTRY_MAP.get(interfaceName);
    }


}
