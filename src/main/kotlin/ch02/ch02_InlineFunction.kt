package ch02

//인라인 함수는 프로그램의 성능 및 메모리 최적화를 향상시키는 개선된 기능으로 함수 정의를 호출할 때마다 그것을 인라인으로 대체할 수 있도록 컴파일러가 지시할 수 있다.
fun doSomeStuff(a: Int = 0) = a + (a * a)
inline fun doSomeStuffInline(a: Int = 0) = a + (a * a)

//inline 키워드로 고차함수를 선언하면 inline 키워드는 함수 자체와 전달된 람다에 모두 영향을 끼친다.
inline fun highOrderFuncInline(a: Int, validityCheckFunc: (a: Int) -> Boolean) {
    if (validityCheckFunc(a)) {
        println("a $a is Valid")
    } else {
        println("a $a is Invalid")
    }
}


fun main() {
    for (i in 1..10) {
        //println("$i Output ${doSomeStuff(i)}")
        //println("$i Output ${doSomeStuffInline(i)}")
        highOrderFuncInline(12, { a: Int -> a.isEven() }) //validityCheckFunc 모든 호출을 람다로 대체
        highOrderFuncInline(19, { a: Int -> a.isEven() })
    }
}
