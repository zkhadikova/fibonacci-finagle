namespace scala com.zarina.fibonacci.thrift

exception FibonacciInputException {}

service FibonacciService {
  i64 getFibonacciAt(1: i32 index) throws (1: FibonacciInputException ex);
}

