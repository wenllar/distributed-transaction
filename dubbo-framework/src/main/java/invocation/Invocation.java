package invocation;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Builder
@Getter
@Setter
@ToString
public class Invocation implements Serializable {

    private String interfaceName;

    private String methodName;

    private Class[] paramType;

    private Object[] params;

}
