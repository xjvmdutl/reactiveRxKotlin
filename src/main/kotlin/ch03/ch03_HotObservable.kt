package ch03

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

/*
fun main() {
    //콜드 옵저버블은 수동적이며 구독이 호출될 떄까지 아무것도 내보내지 않지만 핫 옵저버블은 반대로 배출을 시작하기 위해 구독할 필요가 없다
    //핫 옵저버블은 tv채널과 비슷하게 사용자가 시청 여부와 상관없이 콘텐츠를 계속 브로드캐스팅한다.
    //핫 옵저버블은 이벤트와 유사하며 이벤트에는 데이터가 포함될 수 있지만 시간에 민감한 특징을 가진다 -> 최근 가입한 Observer가 이전에 내보낸 데이터를 놓칠수 있기 떄문
    //ConnectableObservable은 옵저버블, 심지어 콜드 옵저버블을 핫 옵저버블로 바꿀 수 있다.
    //subscribe 호출로 배출을 시작하는 대신 connect 메서드를 호출한 후 활성화된다.(connect를 호출하기 전에는 반드시 subscribe를 호출해야한다)
    //connect를 호출한 후 구독하는 모든 호출은 이전에 생성도니 배출을 놓치게 된다
    val connectableObservable = listOf("String 1", "String 2", "String 3", "String 4").toObservable().publish() //콜드 업저버블을 connectableObservable로 변환했다
    connectableObservable.subscribe { //connectableObservable을 구독했다
        println("Subscription 1: $it")
    }
    connectableObservable.map(String::reversed).subscribe { //map연산자를 사용해 String을 뒤집었다
        println("Subscription 2: $it")
    }
    connectableObservable.connect() //connect 호출, 2개의 옵저버에서 배출
    connectableObservable.subscribe {
        println("Subscription 3: $it")
    } //배출을 받지 못함(subscribe 호출은 어떤 배출도 수신하지 못했으며, connect 이후에 일어난 모든 새로운 구독은 이전에 생성된 배출을 놓치게 된다)
    connectableObservable.connect() //connect 호출, 2개의 옵저버에서 배출
}
 */
fun main() {
    //Observable.interval 메서드로 옵저버블을 생성 후 connect 이후의 구독에 약간의 공간을 줄 수 있다.
    val connectableObservable = Observable.interval(100, TimeUnit.MILLISECONDS).publish()
    connectableObservable.subscribe{ println("Subscription 1: $it") } //구독1
    connectableObservable.subscribe{ println("Subscription 2: $it") } //구독2
    connectableObservable.connect() //connect 호출
    runBlocking { delay(500) }

    connectableObservable.subscribe{ println("Subscription 3: $it") } //구독 3 //이전의 구독을 모두 놓친것을 알 수 있다.
    runBlocking { delay(500) }
}
