namespace scala com.zarina.fibonacci.thrift

service FibonacciService {
  i64 getFibonacciAt(1: i32 index);
}

