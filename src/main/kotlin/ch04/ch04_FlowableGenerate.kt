package ch04

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

//백프레셔를 고려할 때 Flowable.fromIterable()을 사용한다. -> 원천을 Iterator로 변환 할 수 있을 때마다 Flowable.fromIterable()을 사용하고 Flowable.generate()는 좀더 복잡한 설정이 필요한 상황에서 사용하자
fun main() {
    val flowable = Flowable.generate<Int> { //요청시 아이템을 생성하고 이를 배출한다
        //generate 는 원천으로서 사용할 람다를 허용하는데 이 람다는 Flowable.create()와 유사하게 보일 수 있지만 달리 아이템을 요청할 때마다 이를 호출한다
        it.onNext(GenerateFlowableItem.item)
    }
    flowable
        .map(::MyItemFlowable)
        .observeOn(Schedulers.io())
        .subscribe{
            runBlocking { delay(100) }
            println("Next $it")
        }
    runBlocking { delay(7000000) }
}
data class MyItemFlowable(val id: Int){
    init {
        println("MyItemFlowable Created $id")
    }
}
object GenerateFlowableItem {
    var item: Int = 0
        get() {
            field += 1
            return field
        }
}