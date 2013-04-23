package com.thoughtworks.main;

import com.thoughtworks.utils.Lang;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;

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
            throw Lang.makeThrow("start server failed %s", ex.getMessage());
        }
    }

    public static void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            Lang.makeThrow("Stop server failed");
        }
    }
}
