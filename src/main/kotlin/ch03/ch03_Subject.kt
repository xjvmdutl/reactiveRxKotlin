package ch03

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

fun main() {
    /*
    //Subject 는 기본적으로 옵저버블과 옵저버의 조합이며, 모두의 공통된 동작을 가지고 있다.
    //핫 옵저버블과 마찬가지로 내부 Observer 목록을 유지하고 배출 시에 가입한 모든 옵저버에게 단일 푸시를 전달한다.
    /**
     * Subject가 제공하는 것
     * 옵저버블이 가져야 하는 모든 연산자를 갖고 있다
     * 옵저버와 마찬가지로 배출된 모든 값에 접근할 수 있다
     * Subject가 완료/오류/구독 해지 된 후에는 재사용할 수 없다.
     * 그 자체로 가치를 전달한다 -> onNext를 사용해 값을 Subject(Observer) 측에 전달하면 Observable에서 접근 가능하게 된다
     */
    val observable = Observable.interval(100, TimeUnit.MILLISECONDS)
    val subject = PublishSubject.create<Long>()//Subject 생성 -> PublishSubject는 구독 시점 이후 Observable 소스가 배출한 항목만 Observer에게 전달한다
    observable.subscribe(subject) //subject 인스턴스를 사용해 옵저버블 인스턴스의 배출을 구독한다.
    subject.subscribe{  //subject 인스턴스를 사용해 Subject 인스턴스에 의한 배출에 접근하기 위해 람다를 사용해 구독
        println("Received $it")
    }
    runBlocking { delay(1100) } //모든 코드 실행을 완료할 때까지 스레드를 차단하면서 호출 스레드 내부에서 코루틴 컨텍스트를 Mocking한다
     */
    val observable = Observable.interval(100, TimeUnit.MILLISECONDS)
    val subject = PublishSubject.create<Long>()
    observable.subscribe(subject)
    subject.subscribe{
        println("Subscription 1 Received $it")
    }
    runBlocking { delay(1100) }
    subject.subscribe{ //subject를 다시 구독 -> 처음 11회의 뱌출 이후의 데이터를 받는다
        println("Subscription 2 Received $it")
    }
    runBlocking { delay(1100) }
}