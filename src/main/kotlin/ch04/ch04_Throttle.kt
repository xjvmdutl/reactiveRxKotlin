package ch04

import io.reactivex.Flowable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

//buffer, window  연산자는 배출을 수집하지만, throttle 연산자는 배출을 생략한다.
fun main() {
    val flowable = Flowable.interval(100, TimeUnit.MILLISECONDS)
    flowable.throttleFirst(200, TimeUnit.MILLISECONDS) //200밀리초마다 발생한는 첫번째 배출을 건너 띈다
        .subscribe(::println)
    runBlocking { delay(1000) }
}