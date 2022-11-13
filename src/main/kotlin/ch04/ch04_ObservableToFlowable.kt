package ch04

import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

//Observable.toFlowable 연산자는 백프레셔를 지원하지 않는 원천에서 BackpressureStrategy를 구현하는 방법을 제공한다.
//Observable을 Flowable로 바꿔준다.
fun main() {
    /*
    val source = Observable.range(1, 1000)
    source.toFlowable(BackpressureStrategy.BUFFER) //모든 처리 가능
        .map { MyItem7(it) }
        .observeOn(Schedulers.io())
        .subscribe{
            print("Rec. $it;\t")
            runBlocking { delay(1000) }
        }
    runBlocking { delay(10000) }
     */
    /*
    val source = Observable.range(1, 1000)
    source.toFlowable(BackpressureStrategy.ERROR) //에러가 발생 ->다운 스트림이 업스트림을 따라가지 못하기 떄문
        .map { MyItem7(it) }
        .observeOn(Schedulers.io())
        .subscribe{
            print("Rec. $it;\t")
            runBlocking { delay(1000) }
        }
    runBlocking { delay(10000) }
     */
    val source = Observable.range(1, 1000)
    source.toFlowable(BackpressureStrategy.DROP)
        .map { MyItem7(it) }
        .observeOn(Schedulers.io())
        .subscribe{
            print("Rec. $it;\t")
            runBlocking { delay(1000) }
        }
    runBlocking { delay(700000) }
}

data class MyItem7(val id: Int){
    init {
        println("MyItem Init $id")
    }
}