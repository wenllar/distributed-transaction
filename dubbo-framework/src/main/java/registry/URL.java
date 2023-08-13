package registry;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class URL implements java.io.Serializable{

    private String host;
    private int port;
}
