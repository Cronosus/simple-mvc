###Run tests:

`gradle test`

###Start sample app in embeded jetty:

run `gradle sample:start` and visit `http://localhost:8080/sample/pet/index`


Tasks:
1. integration tests
    1.1 create a sample webapp that use my mvc framework.       DONE
    1.2 launch this webapp and test if it works as expected.    DONE
    1.3 make this sample webapp as a sub project.               DONE
    1.4 deploy this webapp to jetty.                            DONE

2. create dispatch servlet that dispatch request into controller based on url mapping
    2.1 initialize this dispatch servlet DONE
    2.2 resolve controller from url mapping DONE
            2.3 extract parameters from request                 DONE
            2.6 nested structure
    2.4 get controller instance from ioc, and dispatch request to it DONE
    2.5 render view  DONE

3. service injection DONE

4. run in a embedded web container DONE