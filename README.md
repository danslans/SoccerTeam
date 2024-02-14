# SoccerTeam

## Dependencies:
To run this project you require JDK 17
    java 17

To view the coverage code, you can run:

    ./gradlew test jacocoTestReport

To run in local:

    .\gradlew bootjar   
    java -jar .\build\libs\footballteam-0.0.1-SNAPSHOT.jar

To view documentation of endpoints exposes see the folder, and the documentation
is based from OpenApi

    /docApi/index.html
    https://danslans.github.io/SoccerTeam/docApi/

Additional to test endpoints on production do you can access to heroku platform
but in this repository we are file postman collection ```postman_collection.json```

        https://stoccer-team-79e4c71cb910.herokuapp.com/

I invite you to see file ```diagram.drawio``` to here you can see all use cases,
and diagrams from this Api