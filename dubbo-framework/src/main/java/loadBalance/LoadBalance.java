package loadBalance;

import registry.URL;

import java.util.List;
import java.util.Random;

public class LoadBalance {

    public static URL loadBalance(List<URL> urlList){
         int index = new Random().nextInt(urlList.size());
        return urlList.get(index);
    }
}
