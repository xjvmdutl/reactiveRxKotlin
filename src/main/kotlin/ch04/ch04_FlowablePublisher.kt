package ch04

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

//플로어블은 옵저버 대신 백프레셔 호환이 가능한 구독자를 사용한다 -> 구독자가 일부 추가 기능과 백프레셔를 동시에 지원하기 때문
fun main() {
    Flowable.range(1, 15)
        .map { MyItem6(it) }
        .observeOn(Schedulers.io())
        .subscribe(object: Subscriber<MyItem6> { //Subscriber 생성
            lateinit var subscription: Subscription
            override fun onNext(item: MyItem6?) {
                runBlocking { delay(50) }
                println("Subscriber received " + item!!)
                if (item.id == 5) {//
                    println("Requesting two more")
                    subscription.request(2) //초기 원하는 배출량
                    //전체 배출을 받기 위해서는 Long.MAX_VALUE 를 받으면 된다.
                    //request는 Subscriber가 호출되고 나서 업스트림에서 대기해야 하는 배출량을 요청한다.
                    //Subsribe가 더 요청할 때까지 요청 이후의 더 이상의 모든 배출은 무시
                    //7이후에는 더이상 전달되지 않는다
                }
            }

            override fun onSubscribe(subscription: Subscription) {
                this.subscription = subscription
                subscription.request(5)
            }


            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

            override fun onComplete() {
                println("Done!")
            }
        })
    runBlocking { delay(10000) }
}

data class MyItem6(val id: Int) {
    init {
        println("MyItem Created $id")
    }
}