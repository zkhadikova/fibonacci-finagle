# fibonacci-finagle
HTTP Proxy service and Fibonacci thrift server that work together to deliver a Fibonacci calculation of a given number.

## General Info
Language: Scala
Frameworks used:
 * Twitter Finagle
 * Github Finch

## Project Structure
* fibonacci-finagle -- root project
  - common -- common part contains thrift classes
  - proxy-service -- rest API for getting fibonacci number
  - fibonacci-server -- Thrift server to perform actual calculation

## Running
Use SBT to run 
```
sbt
>project fibonacciServer
>run
```
```
sbt
>project proxyService
>run
```

Or build jars with sbt 
```
sbt
>assemby
```
Jars will be put into `<module>/target/scala-2.12/` dir. And then execute jars
```
java -jar fibonacciServer-assembly-0.0.1.jar
```

## Testing
Rest endpoint to test 
`http://localhost:8081/fibonacci/<number>`
