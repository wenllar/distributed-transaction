package registry.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LocalRegistry {//implements Registry

    static final Map<String, String> REGSTRY_MAP = new HashMap<>();

    private LocalRegistry(){

    }
    public static boolean regist(String interfaceName, String implService){
        if(Objects.isNull(interfaceName) || Objects.isNull(interfaceName)){
            throw new NullPointerException();
        }
        return null == REGSTRY_MAP.put(interfaceName, implService);
    }

    public static boolean unRegist(String interfaceName){
        if(Objects.isNull(interfaceName) || Objects.isNull(interfaceName)){
            throw new NullPointerException();
        }
       return null == REGSTRY_MAP.remove(interfaceName);
    }

    public static String getService(String interfaceName) {
        return REGSTRY_MAP.get(interfaceName);
    }


}
