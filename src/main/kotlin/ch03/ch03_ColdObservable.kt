package ch03

import io.reactivex.rxkotlin.toObservable

fun main() {
    val observable = listOf("String 1", "String 2", "String 3", "String 4").toObservable() //Observable 선언
    //각 구독마다 처음부터 아이템을 배출한다 -> 콜드 옵져버블
    //콜드 옵저버블은 구독 시에 실행을 시작하고 subscribe가 호출되면 아이템을 푸시하기 시작하고 각 구독에서 아이템의 동일한 순서로 푸시한다
    observable.subscribe({ //구독
        println("Received $it")
    }, {
        println("Error ${it.message}")
    }, {
        println("Done")
    })
    observable.subscribe({ //구독
        println("Received $it")
    }, {
        println("Error ${it.message}")
    }, {
        println("Done")
    })
}