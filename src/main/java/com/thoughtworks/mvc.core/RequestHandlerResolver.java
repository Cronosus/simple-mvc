package com.thoughtworks.mvc.core;

import com.thoughtworks.di.core.Injector;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandlerResolver {

    private static final Pattern COLLECTION_URL_PATTERN = Pattern.compile("(/\\w+)");
    private static final Pattern NEW_URL_PATTERN = Pattern.compile("(/\\w+)/new");
    private static final Pattern EDIT_URL_PATTERN = Pattern.compile("(/\\w+)/(\\d+)/edit");
    private static final Pattern MEMBER_URL_PATTERN = Pattern.compile("(/\\w+)/(\\d+)");

    private final Injector container;
    private final Router router;

    public RequestHandlerResolver(Injector container, Router router) {
        this.container = container;
        this.router = router;
    }

    public RequestHandler resolve(HttpServletRequest request) {


        String action = "";
        String module = "";
        String id = "";

        Matcher collectionUrlMatcher = COLLECTION_URL_PATTERN.matcher(request.getRequestURI());
        Matcher newUrlMatcher = NEW_URL_PATTERN.matcher(request.getRequestURI());
        Matcher editUrlMatcher = EDIT_URL_PATTERN.matcher(request.getRequestURI());
        Matcher memberUrlMatcher = MEMBER_URL_PATTERN.matcher(request.getRequestURI());

        if (collectionUrlMatcher.matches()) {
            module = collectionUrlMatcher.group(1);
            action = "index";
        } else if (newUrlMatcher.matches()) {
            module = newUrlMatcher.group(1);
            action = "new";
        } else if (editUrlMatcher.matches()) {
            module = editUrlMatcher.group(1);
            action = "edit";
        } else if (memberUrlMatcher.matches()) {
            module = memberUrlMatcher.group(1);
            id = memberUrlMatcher.group(2);
            action = "show";
        }

        if (!module.isEmpty() && !action.isEmpty()) {
            Controller controller = (Controller) container.get(router.classFor(module));
            return new RequestHandler(controller, action);
        } else {
            return null;
        }

    }
}
