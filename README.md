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
>sbt
>project fibonacciServer
>run
```
```
>sbt
>project proxyService
>run
```

## Testing
Rest endpoint to test 
http://localhost:8081/fibonacci/<number>
