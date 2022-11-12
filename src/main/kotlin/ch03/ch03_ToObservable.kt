package ch03

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toObservable

fun main() {
    val observer = object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            println("New Subscription")
        }

        override fun onError(e: Throwable) {
            println("Error Occured ${e.message}")
        }

        override fun onComplete() {
            println("All Complete")
        }

        override fun onNext(item: String) {
            println("Next $item")
        }
    } //Observer 생성

    val list = listOf("String 1", "String 2", "String 3", "String 4")
    //코틀린의 확장함수 덕에 해당 함수의 메서드처럼 사용할 수 있다
    val observable = list.toObservable()
    observable.subscribe(observer)
}