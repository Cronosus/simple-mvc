package com.thoughtworks.mvc.core;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/")
@WebInitParam(name = "", value ="")
public class DispatchServlet extends HttpServlet {
}
