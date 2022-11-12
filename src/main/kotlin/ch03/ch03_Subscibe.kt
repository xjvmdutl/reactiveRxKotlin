package ch03

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

/**
 * OnNext : 아이템을 하나씩 넘겨주기 위해서 옵저버블은 옵저버의 이 메서드를 호출한다.
 * OnComplete: 옵저버블이 onNext를 통한 아이템 전달이 종료됐음을 알리고 싶을 때 옵저버의 onComplete를 호출한다.
 * onError : 옵저버블에서 에러가 발생했을 때 옵저버에 정의된 로직이 있다면 onError를 호출하고 그렇지 않다면 예외를 발생시킨다
 * onSubscribe:옵저버블이 새로운 Observer를 구독할 때마다 호출된다.
 */
//Subscribe 연산자는 Observable을 Observer에 연결하는 매개체의 용도로 사용된다
//Subscribe 연산자에 대해 1개에서 3개의 메서드(onNext, onComplete, onError)를 전달할 수 있고, Observer 인터페이스의 인스턴스를 연산자에 전달해 연결할 수도 있다
fun main() {
    /*
    val observable = Observable.range(1, 5) //Observable 인스턴스를 생성

    //subscribe 메서드에 인수로 3가지 메서드를 전달했다(onNext, onError, onComplete)
    observable.subscribe({
        //OnNext
        println("Next $it")
    }, {
        //OnError
        println("Error ${it.message}")
    }, {
        //OnComplete
        println("Done")
    })

    //Observer 인터페이스의 인스턴스를 전달했다
    val observer = object : Observer<Int> {
        override fun onNext(item: Int) {
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
    }
    observable.subscribe(observer)
     */
    //일정기간이 지나고 구독을 중지하려면 어떻게 해야하나 -> 옵져버의 onSubscribe 메서드의 매개변수를 활용하자
    //구독하는 동안 Observer 인스턴스 대신 메서드를 전달하면 subscribe 연산자는 Disposable의 인스턴스를 반환하고,
    // Observer 인스턴스를 전달햇다면 onSubscribe 메서드의 매개변수에서 Disposable의 인스턴스를 얻을 수 있다.
    //해당 Disposable 인터페이스의 인스턴스를 사용해 주어진 시간에 전송을 중지 할 수 있다
    runBlocking {
        val observable = Observable.interval(100, TimeUnit.MILLISECONDS) //0부터 시작해 정수를 순차적 출력 -> 프로그램 실행이 멈출떄까지 동작
        val observer = object : Observer<Long> {
            lateinit var disposable: Disposable //lateinit 으로 나중에 변수가 초기화 되게 설정한다
            override fun onSubscribe(d: Disposable) {
                disposable = d//수신된 매개변수값을 disposable 변수에 할당
            }
            override fun onError(e: Throwable) {
                println("Error ${e.message}")
            }

            override fun onComplete() {
                println("Complete")
            }

            override fun onNext(item: Long) {
                println("Received $item")
                if (item >= 10 && !disposable.isDisposed) { //seq가 10에 도달 된 후 중지 -> 10보다 크고 해지되지 않았다면
                    disposable.dispose() //구독 해지
                    /*
                    interface Disposable {
                        /**
                         * 리소스를 처리한다 -> 연산은 멱등성을 가져야한다
                         */
                        fun dispose()

                        /**
                         * 리소스가 처리됐다면 참을 반환한다
                         */
                        val isDisposed: Boolean
                    }
                     */

                    println("Disposed")
                }
            }
        }
        observable.subscribe(observer)
        delay(1500)
    }
}