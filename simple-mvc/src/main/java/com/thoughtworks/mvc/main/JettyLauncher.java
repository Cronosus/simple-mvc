package com.thoughtworks.mvc.main;

import com.thoughtworks.mvc.utils.MVCHelper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;

import static com.thoughtworks.simpleframework.util.Lang.makeThrow;

public class JettyLauncher {

    private static Server server;

    public static void start(String webAppPath, String contextPath) {
        try {
            server = new Server(8080);
            WebAppContext webAppContext = new WebAppContext(new File(webAppPath).getAbsolutePath(), contextPath);
            server.setHandler(webAppContext);

            server.start();
        } catch (Exception ex) {
            stop();
            throw makeThrow("start server failed %s", ex.getMessage());
        }
    }

    public static void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            makeThrow("Stop server failed");
        }
    }
}
