package ch03

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

//Observable.Just : 넘겨진 인자만을 배출하는 옵저버블을 생성한다.
//Iterable 인스턴스를 Observable.just에 단일 인자로 넘기면 전체 목록을 하나의 아이템으로 배출된다
//이는 Iterable 내부의 각각의 아이템을 Observable로 생성하는 Observable.from과는 다르다.
/**
 * 인자와 함께 Observable.just 호출
 * Observable.just는 옵저버블 생성
 * onNext 알림을 통해 각각의 아이템을 내보냄
 * 모든 인자의 제출이 완료되면 onComplete 알림을 실행
 */
fun main() {
    val observer = object : Observer<Any> {
        override fun onNext(item: Any) {
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
    Observable.just("A String").subscribe(observer)
    Observable.just(54).subscribe(observer)
    Observable.just(
        listOf("String 1", "String 2", "String 3", "String 4")
    ).subscribe(observer)
    Observable.just(
        mapOf(
            Pair("Key 1", "Value 1"),
            Pair("Key 2", "Value 2"),
            Pair("Key 3", "Value 3"),
            Pair("Key 4", "Value 4"),
        )
    ).subscribe(observer)
    Observable.just(arrayListOf(1, 2, 3, 4, 5, 6)).subscribe(observer)
    //Iterable 을 구현하고 있는 객체 모두 단일 아이템 취급이 된다
    Observable.just(
        "String 1", "String 2", "String 3" //해당 아이템들은 각각의 인자를 별개의 아이템으로 받아들여 내보내지고 있다.
    ).subscribe(observer)
}