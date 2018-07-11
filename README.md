# Nick's todo App

### Setup

##### Prerequisites

Install [Java 8 Jdk](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

### Build and package the app 
From the \<path-checked-out-to\>\root directory run ./gradlew build

This will put the packaged app in <path-checked-out-to>todo\build\libs

### Run the API <Change as appropriate to your OS>

From a prompt navigate to <path-checked-out-to>todo\build\libs

run the command 
\<path-to-java\>\java.exe -jar todo-0.0.1-SNAPSHOT.jar

e.g. For Windows:  "C:/Program Files/Java/jdk1.8.0_131/bin/"java.exe  -jar todo-0.0.1-SNAPSHOT.jar


This command serves the API at `http://localhost:8080/api/todo/`


example commands:



| Method |  Url                                       | Body                                                                                                                                    | What it does                                                             |
|--------|--------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------|                                 
| Get    | http://localhost:8080/api/todo/            |                                                                                                                                         | return all todos                                                         |
| Get    | http://localhost:8080/api/todo/?status=ALL |                                                                                                                                         | return all todos with given status. Valid status are ALL, PENDING or DONE|
| Get    | http://localhost:8080/api/todo/\<id\>      |                                                                                                                                         | return the todo identified by \<id\                                      |
| Post   | http://localhost:8080/api/todo             |{"id":"4e3f0694-48d1-48f7-b997-b096df9be6ed","name":"Trash","description":"take out the trash","dueDate":"2018-07-08","status":"PENDING"}| create a todo                                                            |
| Put    | http://localhost:8080/api/todo/\<id\>      |{"name":"Trash","description":"Kids take out the trash","dueDate":"2018-07-08","status":"PENDING"}                                       | update todo with \<id\>                                                  |
| Delete | http://localhost:8080/api/todo/\<id\>      |                                                                                                                                         | delete todo with \<id\>                                                  |
