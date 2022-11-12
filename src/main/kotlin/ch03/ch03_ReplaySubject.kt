package ch03

import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.ReplaySubject

fun main() {
    //갖고 있는 모든 아이템을 옵저버의 구독시점과 상관없이 다시 전달하며, 콜드 옵저버브로가 유사하다
    val subject = ReplaySubject.create<Int>()
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