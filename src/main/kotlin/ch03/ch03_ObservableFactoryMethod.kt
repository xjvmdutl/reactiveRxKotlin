package ch03

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

fun main() {
    val observer = object : Observer<Any> {
        override fun onComplete() {
            println("All Completed")
        }

        override fun onNext(item: Any) {
            println("Next $item")
        }

        override fun onError(e: Throwable) {
            println("Error Occured ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("New Subscription")
        }
    } //Observer 생성

    Observable.range(1, 10).subscribe(observer) //Observable.range 팩토리 메서드로 옵저버블 생성 => 제공된 start 부터 count 만큼의 정수를 내보낸다
    Observable.empty<String>().subscribe(observer) //Observable.empty 펙토리 메서드로 옵저버블 생성 => onNext로 항목을 내보내지 않고 즉시 onComplete 발생

    runBlocking {
        Observable.interval(300, TimeUnit.MILLISECONDS).subscribe(observer) //지정된 간격만큼의 숫자를 0부터 순차적으로 내보낸다.(구독을 취소하거나 프로그램이 종료될때까지 계속 된다)
        delay(900)
        Observable.timer(400, TimeUnit.MILLISECONDS).subscribe(observer) //지정된 시간이 경과한 후 1번만 실행
        delay(450)
    }
}