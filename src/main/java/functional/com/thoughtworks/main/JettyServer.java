package functional.com.thoughtworks.main;

import com.thoughtworks.utils.Lang;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;

public class JettyServer {

    private static Server server;

    public static void start() {
        try {
            server = new Server(8080);
            WebAppContext webAppContext = new WebAppContext(new File("src/test/webapp").getAbsolutePath(), "/sample");
            server.setHandler(webAppContext);

            server.start();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public static void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            Lang.makeThrow("Stop server failed");
        }
    }

    public static void main(String[] args) {
        start();
    }
}
