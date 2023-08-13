package handler;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public interface HttpHandler {

    public void handle(ServletRequest req, ServletResponse res) throws IOException, ClassNotFoundException;
}
