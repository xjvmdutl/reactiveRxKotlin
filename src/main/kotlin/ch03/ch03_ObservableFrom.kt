package ch03

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

//from 메서드를 사용하면 모든 코틀린 구조체로붙터 Observable 인스턴스를 생성할 수 있다
fun main() {
    val observer = object : Observer<String> {
        override fun onNext(item: String) {
            println("Next $item")
        }

        override fun onSubscribe(d: Disposable) {
            println("New Subsciption")
        }

        override fun onError(e: Throwable) {
            println("Error Occured ${e.message}")
        }

        override fun onComplete() {
            println("All Completed")
        }
    }//Observer 생성

    val list = listOf("String 1", "String 2", "String 3", "String 4")
    val observableFromIterable = Observable.fromIterable(list) //fromIterable를 통해 Iterable 인스턴스로 부터 옵저버블을 생성
    observableFromIterable.subscribe(observer)

    val callable = object : Callable<String> {
        override fun call(): String {
            return "From Callable"
        }
    }
    val observableFromCallable = Observable.fromCallable(callable)//Callable 인스턴스에서 옵저버블을 생성
    observableFromCallable.subscribe(observer)

    val future = object : Future<String> {
        override fun cancel(mayInterruptIfRunning: Boolean): Boolean = false

        override fun isCancelled(): Boolean = false

        override fun isDone(): Boolean = true

        override fun get(): String = "Hello From Future"

        override fun get(timeout: Long, unit: TimeUnit): String = "Hello From Future"
    }
    val observableFromFuture = Observable.fromFuture(future) //Future 인스턴스에서 옵저버블을 파생
    observableFromFuture.subscribe(observer)

}