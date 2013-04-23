<html>
  <head>
    <title>New Pet</title>
  </head>
  <body>
    <div class="container">
      <form action = "/sample/pet/create" method="POST">
        <div class="fieldset">
          <label>Name:</label><input type = "text" name="name"/>
        </div>
        <div class="fieldset">
          <label>Age:</label><input type = "text" name="age"/>
        </div>
        <div class="fieldset">
          <label>Gender:</label>
          <select name="gender">
            <option value="male">Male</option>
            <option value="female">Female</option>
          </select>
        </div>
        <div class="fieldset">
          <input type='submit'/>
        </div>
      </form>
    </div>
  </body>
</html>