package ch01

import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable

fun main() {
    var list:List<Any> = listOf("One", 2, "Three", "Four", 4.5, "Five", 6.0f) //1
    var observable= list.toObservable()  //observable 인스턴스 생성
    observable.subscribeBy( //observable 인스턴스를 구독한다 //구독했기 때문에 모든 변경사항은 onNext로 push되고 모든 데이터가 push되면 onComplete, 에러 발생시 onError 호출
        onNext = { println(it) }        ,
        onError = {it.printStackTrace()},
        onComplete = { println("Done") }
    )
}