package ch03

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

//Observable.create 메서드로 옵저버블을 직접 생성할 수 있다.
//관찰 대상자를 ObservableEmitter<T> 인터페이스의 인스턴스로 입력받는다
//사용자가 지정한 데이터 구조를 사용하거나 내보내는 값을 제어하려고 할 때 유용하다.
fun main() {
    val observer = object : Observer<String> {
        override fun onComplete() {
            println("All Completed")
        }

        override fun onNext(item: String) {
            println("Next $item")
        }

        override fun onError(e: Throwable) {
            println("Error Occured ${e.message}")
        }

        override fun onSubscribe(d: Disposable) {
            println("New Subscription")
        }
    } //Observer 생성

    val observable = Observable.create<String> {
        //Observable.create 메서드로 옵저버블을 생성하였고, onNext 메서드의 도움으로 5개의 문자열 내보낸 후 onComplete 메서드로 완료되었음을 알려준다
        it.onNext("Emit 1")
        it.onNext("Emit 2")
        it.onNext("Emit 3")
        it.onNext("Emit 4")
        it.onNext("Emit 5")
        it.onComplete()
    }

    observable.subscribe(observer)

    val observable2 = Observable.create<String> {
        //Observable.create 메서드로 옵저버블을 생성하였고, onNext 메서드의 도움으로 5개의 문자열 내보낸 후 onError 메서드로 예외를 호출한다
        it.onNext("Emit 1")
        it.onNext("Emit 2")
        it.onNext("Emit 3")
        it.onNext("Emit 4")
        it.onNext("Emit 5")
        it.onError(Exception("My Custom Exception"))
    }
    observable2.subscribe(observer)
}