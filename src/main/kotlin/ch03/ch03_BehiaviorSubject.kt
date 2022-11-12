package ch03

import io.reactivex.subjects.BehaviorSubject

fun main() {
    //AsyncSubject 장점 + PublishSubject 장점을 모두 취한다.
    //멀티캐스팅으로 동작하며, 구독 전의 마지막 아이템과 구독 후 모든 아이템을 배출한다.
    //내부 옵저버 목록을 유지하는 데 중복 전달 없이 모든 옵저버에게 동일한 배출을 전달
    val subject = BehaviorSubject.create<Int>()
    subject.onNext(1)
    subject.onNext(2)
    subject.onNext(3)
    subject.onNext(4)
    subject.subscribe({
        println("S1 Received $it")
    }, {
        it.printStackTrace()
    }, {
        println("S1 Complete")
    })
    subject.onNext(5)
    subject.subscribe({
        println("S2 Received $it")
    }, {
        it.printStackTrace()
    }, {
        println("S2 Complete")
    })
    subject.onComplete()
}