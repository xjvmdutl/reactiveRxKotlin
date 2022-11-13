package ch04

import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

//BackpressureStrategy.MISSING은 Backpressure 전략을 구현하지 않으므로 플로어블에게 어떤 전략을 따를지 명시적으로 알려줄 필요가 있음을 의미한다.
//onBackpressureXXX() 연산자에는 3가지 주요 유형이 있다.
/**
 * onBackpressureBuffer()
 * onBackpressureDrop()
 * onBackpressureLatest()
 */
fun main() {
    //onBackpressureBuffer은 BackpressureStrategy.BUFFER의 용도로 사용된다.
    //버퍼크기, 크기 제한 여부 와 같은 몇가지 추가 구성 옵션을 얻을 수 있다.
    //기본 동작은 구성을 생략해도 동작한다.
    /*
    val source = Observable.range(1, 1000)
    source.toFlowable(BackpressureStrategy.MISSING) //플로어블 인스턴스 생성
        //.onBackpressureBuffer()//백프레셔를 지원하기 위해 onBackpressureBuffer를 사용했다.
        .onBackpressureBuffer(20)//버퍼 크기를 정해줄 수도 있다
        .map { MyItem8(it) }
        .observeOn(Schedulers.io())
        .subscribe{
            println(it)
            runBlocking { delay(1000) }
        }
    runBlocking { delay(600000) }
     */
    /*
    val source = Observable.range(1, 1000)
    source.toFlowable(BackpressureStrategy.MISSING)
        .onBackpressureDrop{ print("Dropped $it;\t") } //onBackpressureDrop 메서드 사용 -> 컨슈머 인스턴스에 전달되는 구성옵션을 제공하는데 처리가 거부된 배출량을 소비하므로 추가 처리가 가능하다
            //버퍼 크기가 128이기 떄문에 플로어블은 128 이후에 배출을 처리하지 못하고 있다
        .map { MyItem8(it) }
        .observeOn(Schedulers.io())
        .subscribe{
            println(it)
            runBlocking { delay(1000) }
        }
    runBlocking { delay(600000) }
     */

    val source = Observable.range(1, 1000)
    source.toFlowable(BackpressureStrategy.MISSING)
        .onBackpressureLatest()//BackpressureStrategy.LATEST와 똑같은 방식으로 동작한다
        //다운스트림이 바쁘고 배출을 따라갈 수 없을 떄 최신 배출을 제외한 모든 배출을 무시한다.
        //다운스트림이 처리하던 작업이 완료되면, 직전에 생성된 마지막 배출이 다음으로 전달된다
        //해당 구성은 어떤것도 제공하지 않는다
        .map { MyItem8(it) }
        .observeOn(Schedulers.io())
        .subscribe{
            println(it)
            runBlocking { delay(1000) }
        }
    runBlocking { delay(600000) }
}

data class MyItem8(val id: Int){
    init {
        println("MyItem Created $id")
    }
}