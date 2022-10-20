# ApiService

Build: 
```shell
mvn clean compile assembly:single
```
Run:
```shell
token=<ACCESS_TOKEN> java -cp target/ApiService-1.0-SNAPSHOT-jar-with-dependencies.jar Main https://gorest.co.in/public/v2/users
```