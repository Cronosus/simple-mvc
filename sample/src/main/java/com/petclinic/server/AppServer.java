package com.petclinic.server;

import com.thoughtworks.main.JettyLauncher;

public class AppServer {
    public static void main(String[] args) {
        JettyLauncher.start("sample/src/main/webapp", "/sample");
    }
}
