package registry;

import java.util.HashMap;
import java.util.Map;

public interface Registry {

    Map<String, String> REGSTRY_MAP = new HashMap<>();

    boolean regist(String interfaceName, String implService);

    boolean unRegist(String interfaceName);

    String getService(String interfaceName);


}
