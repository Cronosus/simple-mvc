#URL mapping
on initialization:

(@Controller(url), @Action(url)) -> {
    url -> controller, action
}

on runtime:

  (module url, action url) -> controller, method
