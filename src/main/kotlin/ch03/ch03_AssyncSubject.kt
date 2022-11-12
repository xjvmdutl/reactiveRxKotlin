package ch03

import io.reactivex.Observable
import io.reactivex.subjects.AsyncSubject

//수신 대기 중인 소스 옵저버블의 마지막 값과 배출만 전달한다.
//AsyncSubject는 마지막 값을 한번만 배출한다.
fun main() {
    /*
    val observable = Observable.just(1, 2, 3, 4) //정수 4개 생성
    val subject = AsyncSubject.create<Int>() //AsyncSubject 생성
    observable.subscribe(subject) //옵저버블 인스턴스에 가입
    subject.subscribe({ //람다를 사용해 가입
        println("Received $it")
    },{
      it.printStackTrace()
    },{
        println("Complete")
    })
    subject.onComplete()
     */
    val subject = AsyncSubject.create<Int>()
    subject.onNext(1)
    subject.onNext(2)
    subject.onNext(3)
    subject.onNext(4)
    //2가지 구독 모두 마지막 5만 출력했다
    subject.subscribe({
        println("S1 Received $it")
    },{
        it.printStackTrace()
    },{
        println("S1 Complete")
    })
    subject.onNext(5)
    subject.subscribe({
        println("S2 Received $it")
    },{
        it.printStackTrace()
    },{
        println("S2 Complete")
    })
    subject.onComplete()
}