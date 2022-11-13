package ch04

import io.reactivex.Flowable

//window() 연산자는 아이템을 컬렉션 형태로 버퍼링하는 대신 다른 프로듀서 형태로 버퍼링한다는 점만 빼면 거의 유사하다
fun main() {
    val flowable = Flowable.range(1, 111)
    flowable.window(10) ///새로운 플로어블 인스턴스로 10개의 배출을 버퍼링한다
        .subscribe { flo ->
            flo.subscribe {
                print("$it, ")
            }
            println()
        }
}