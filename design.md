#URL mapping

|HTTP Verb |Path             |action |used for                                     |
|----------|-----------------|-------|---------------------------------------------|
|GET       |/photos          |index  |display a list of all photos                 |
|GET       |/photos/new      |new    |return an HTML form for creating a new photo |
|POST      |/photos          |create |create a new photo                           |
|GET       |/photos/:id      |show   |display a specific photo                     |
|GET       |/photos/:id/edit |edit   |return an HTML form for editing a photo      |
|PUT       |/photos/:id      |update |update a specific photo                      |
|DELETE    |/photos/:id      |destroy|delete a specific photo                      |

on initialization:

(@At(url), @At(url), @Param(name)) -> {
    url -> controller, action, required param type and number
}
    module url -> controller class
    second url + method -> required param type and name


on runtime:

  (module url, second url,  method) -> controller, action, parsed params

  PUT -> update
  DELETE -> destory
  POST -> create
  GET -> <empty>   -> index
         new       -> new
         <id>      -> show
         <id>/edit -> edit
  required params
