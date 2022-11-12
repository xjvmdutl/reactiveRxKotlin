package ch03

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toObservable

/**
 * 옵저버블 : 컨슈머가 소비할 수 있는 값을 산출해 내는 기본 계산 작업을 갖고있다.
 *          컨슈머가 값을 pull 방식을 사용해 접근하지 않고 오히려 옵져버블이 컨슈머에게 값을 푸시 하는 역할을 한다.
 *          옵저버블은 일련을 연산자를 거친 아이템을 최종 옵저버로 내보내는 푸시 기반의 조합 가능한 이터레이터인 것이다.
 * - 옵저버는 옵저버블을 구독한다.
 * - 옵저버블이 그 내부의 아이템들을 내보내기 시작한다.
 * - 옵저버는 옵저버블에서 내보내는 모든 아이템에 반응한다
 *
 * onNext : 옵저버블은 모든 아이템을 하나씩 이 메서드에 전달
 * onComplete : 모든 아이템이 onNext 메서드를 통과하면 옵저버블은 onComplete 메서드를 호출한다.
 * onError : 옵저버블에서 에러가 발생하면 onError 메서드가 호출돼 정의된 대로 에러를 처리한다 -> onError와 onComplete는 터미널 이벤트도 서로 베타관계이다.
 */
fun main() {
    val observer = object : Observer<Any> { //Observer 인스턴스를 Any 타입으로 지정
        override fun onComplete() { //Observable이 오류없이 모든 아이템을 처리하면 호출된다
            println("All Complete")
        }

        override fun onNext(item: Any) { //옵저버블이 내보내는 각 아이템에 대해 호출된다
            println("Next $item")
        }

        override fun onError(e: Throwable) { //옵저버블에 오류가 발생했을때 호출된다
            println("Error Occured $e")
        }


        override fun onSubscribe(d: Disposable) { //옵저버가 옵저버블을 구독할 때마다 호출된다
            println("Subscribe to $d")
        }
    }

    val observable = listOf("One", 2, "Three", "Four", 4.5, "Five", 6.0f).toObservable() //list(val observable)을 통해 옵저버블을 생성
    observable.subscribe(observer) //observer가 observable을 구독한다

    val observableOnList = Observable.just(
        listOf("One", 2, "Three", "Four", 4.5, "Five", 6.0f),
        listOf("List with Single Item"),
        listOf(1, 2, 3, 4, 5, 6)
    )
    observableOnList.subscribe(observer)
}