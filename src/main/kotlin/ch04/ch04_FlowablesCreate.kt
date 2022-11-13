package ch04

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

fun main() {
    /*
    val observer = object : Observer<Int> {
        override fun onSubscribe(d: Disposable) {
            println("New Subscription")
        }

        override fun onError(e: Throwable) {
            println("Error Occured ${e.message}")
        }

        override fun onComplete() {
            println("All Completed")
        }

        override fun onNext(item: Int) {
            println("Next $item")
        }
    } //Observer 생성

    val observable = Observable.create<Int> {//옵저버블 생성 -> 백프레셔 기능을 제공하지 않는다
        for(i in 1..10){
            it.onNext(i)
        }
        it.onComplete()
    }
    observable.subscribe(observer)
     */
    val subscriber = object : Subscriber<Int> {
        override fun onSubscribe(subscription: Subscription) { //구독자의 인스턴스를 생성
            println("New Subscription")
        }

        override fun onError(e: Throwable) {
            println("Error Occured ${e.message}")
        }

        override fun onComplete() {
            println("All Completed")
        }

        override fun onNext(item: Int) {
            println("Next $item")
        }
    }

    val flowable = Flowable.create<Int>({
        for (i in 1..10) {
            it.onNext(i)
        }
        it.onComplete()
    }, BackpressureStrategy.BUFFER)//Flowable.create를 사용해 인스턴스를 생성
    //첫번째 매개변수는 배출의 원천이 되는 곳이고, 두번째 매개변수는 BackpressureStrategy이다
    //BackpressureStrategy는 다운 스트림이 따라잡을 수 없는 배출이 생길 경우 캐싱/버퍼링/삭제 등 다양한 백프레셔 전략을 설정하도록 도와준다.
    /**
     * BackpressureStrategy 5가지 전략
     * MISSING: 백프레셔 구현을 사용하지 않으며, 다운스트림이 스스로 오버플로우를 처리해야 한다. 이 옵션은 onBackpressureXXX() 연산자를 사용할 떄 유용하다.
     * ERROR: 어떤 백프레셔로도 구현하지 않는데 다운스트림이 소스를 따라잡을 수 없는 경우, MissingBackpressureException 예외를 발생시킨다.
     * BUFFER: 다운 스트림이 배출을 소비할 수 있게 될 떄까지 제한이 없는 버퍼에 저장한다 -> 버퍼 크기를 넘어서는 경우 OOM 에러가 발생할 수 있다.
     * DROP: 다운 스트림이 바쁘고 소비 속도를 계속 유지할 수 없을 때 모든 배출량을 무시한다. -> 다운스트림이 이전 작업을 끝내고 나서 처음으로 배출된 것을 처리하고 그 사이의 값들은 모두 생략된다
     * LATEST: 다운 스트림이 바쁘고 배출을 유지할 수 없는 경우 최신 배출량만을 유지하고 나머지는 모두 무시한다. -> 다운 스트림이 이전 작업을 마치면 작업이 끝나기 직전에 마지막으로 배출된 것을 수신한다.
     */
    flowable
        .observeOn(Schedulers.io())
        .subscribe(subscriber) //구독한다.
    runBlocking { delay(10000) }
}