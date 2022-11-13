package ch04

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

//옵저버블의 유일한 문제 상황은 옵저버가 옵저버블의 속도에 대처할 수 없는 경우다
//옵저버블은 기본적으로 아이템을 동기적으로 옵저버에게 하나씩 푸시해 동작하는데, 옵저버가 시간을 필요로 하는 작업을 처리해야한다면 그 시간이 옵저버블이 각 항목을 배출하는 간격보다 길어질 수도 있다
fun main() {
    /*
    val observable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9) //Observable 생성
    val subject = BehaviorSubject.create<Int>()
    subject.observeOn(Schedulers.computation()) //observeOn은 구독을 실행하는 스레드를 지정하도록 해주고 Schedulers.computation()은 계산을 수행할 스레드를 제공한다
        .subscribe{ //구독
            println("Subs 1 Received $it")
            runBlocking { delay(200) } //시간이 오래 걸리는 구독자
        }
    subject.observeOn(Schedulers.computation())
        .subscribe{
            println("Subs 2 Received $it")
        }
    observable.subscribe(subject) //BehaviorSubject를 구독한 후에 BehaviorSubject를 사용해 옵저버블을 구독하면 BehaviorSubject의 옵저버가 모든 배출을 받을수 있다.
    runBlocking { delay(2000) }
    //실행 결과를 보면 구독 1 숫자를 모두 출력하지 않았는데, 구독 2가 완료되었다. -> 핫옵저버블처럼 행동하지 않았다
    //핫 옵저버블로서의 행동이 멈춘것이 아니라 첫번째 옵저버에서 각 계산이 오래 걸렸기 때문에 각 배출들은 대기열로 가게 된 것이다(OOM 예외를 포함한 많은 문제를 야기한다)
     */
    //해당 예제에서 볼 수 있듯이 배출이 대기열에 쌓이고 있는데 컨슈머는 이전의 배출량을 처리하고 있는 것이다.
    //이 문제를 해결책은 컨슈머와 생산자간에 피드백을 주고 받을 수 있는 채널이며, 이를 통해 컨슈머는 생산자에게 이전 배출의 처리가 완료될 떄까지 기다려야한다고 전달할 수 있다.
    //-> 컨슈머 또는 메시지를 처리하는 미들웨어가 부하가 높은 상태에서 포화상태가 되거나 응답하지 않는것을 막을 수 있다(대신 메시지양을 줄이도록 요구할 수 있으며, 생산자는 생성 속도를 줄이도록 결정할 수 있다)
    val observable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
    observable
        .map(::MyItem) //배출 아이템을 추적하는데 사용 //배출이 발생할 때마다 즉시 map 연산자로 전달돼 MyItem 클래스의 객체를 생성
        .observeOn(Schedulers.computation())
        .subscribe {
            println("Received $it")
            runBlocking { delay(200) }
        }
    runBlocking { delay(2000) }
}

data class MyItem(val id: Int) {
    init {
        println("MyItem Created $id")
    }
}