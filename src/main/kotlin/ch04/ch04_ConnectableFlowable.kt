package ch04

import io.reactivex.rxkotlin.toFlowable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

//옵저버블처럼 ConnectableFlowable은 플로어블과 유사하지만 구독 시점에 아이템 배출을 시작하지 않고 connect 메서드가 호출될 때 시작한다.
//플로어블이 아이템을 배출하기 전에 의도한 모든 구독자가 Flowable.subscribe()을 기다리동록 할 수 있다
fun main() {
    val connectableFlowable = listOf("String 1", "String 2", "String 3", "String 4", "String 5")
        .toFlowable() //Iterable<T>.toFlowable() 확장함수를 사용해 list에서 플로어블을 만든다
        .publish()// 일반 Flowable을 ConnectableFlowable을 생성한다
    connectableFlowable.subscribe {
        println("Subscription 1: $it")
        runBlocking { delay(1000) }
        println("Subscription 1 delay")
    }
    connectableFlowable.subscribe { println("Subscription 2 $it") }
    connectableFlowable.connect()
    //플로어블이 모든 다운스트림이 처리되기를 기다렸다가 완료 후 다시 다음 아이템의 배출을 시작한다

}