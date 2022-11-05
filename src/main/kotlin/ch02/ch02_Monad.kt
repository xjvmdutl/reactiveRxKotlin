package ch02

import io.reactivex.Maybe
import io.reactivex.rxkotlin.subscribeBy

fun main() {
    //모나드는 값을 캡슐화하고 추가 기능을 더해 새로운 타입을 생성하는 구조체이다.
    //Maybe는 모나드로 Int값을 캡슐화하고 추가 기능을 제공한다
    //모나드인 maybe는 값을 포함할 수도 있고 포함하지 않을 수도 있으며 값/오류 여부에 관계없이 완료된다.(오류 = OnError, 성공 = OnSuccess, 값없고 오류없음 = OnComplete)
    //3가지 메서드 모두 터미널 메서드이며 3가지 메서드중 하나는 모나드에 의해 호출되나 다른것은 호출되지 않는다
    val maybeValue:Maybe<Int> = Maybe.just(14)  //maybe 모나드에 14할당

    maybeValue.subscribeBy( //모나드 구독
        onComplete = { println("Completed Empty") },
        onError = { println("Error $it") },
        onSuccess = { println("Completed with value $it") },
    )
    val maybeEmpty:Maybe<Int> = Maybe.empty() //모나드 다시 선언(빈값)
    maybeEmpty.subscribeBy( //모나드 구독
        onComplete = { println("Completed Empty") },
        onError = { println("Error $it") },
        onSuccess = { println("Completed with value $it") },
    )
}