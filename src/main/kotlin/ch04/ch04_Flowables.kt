package ch04

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

//플로어블은 옵저버블의 백프레셔 버전이다 -> 옵저버블은 백프레셔가 불가능하지만, 플로어블은 가능하다.
//플로어블은 연산자를 위해 최대 128개의 항목을 가질 수 있는 버퍼를 제공하며, 컨슈머가 시간이 걸리는 작업을 실행할 때 배출된 항목이 버퍼에서 대기할 수 있다.
fun main() {
    /*
    Observable.range(1, 1000) //1~1000 사이 숫자를 배출
        .map(::MyItem3) //MyItem3 객체 생성
        .observeOn(Schedulers.computation())
        .subscribe({ //구독
            print("Received $it;\t")
            runBlocking { delay(50) } //시간이 오래 걸리는 구독
        }, {
            it.printStackTrace()
        })
    runBlocking { delay(60000) }
    //이와 같은 코드는 OOM과 같은 문제를 야기할 수 있다
     */

    //플로어블은 모든 아이템을 한번에 배출하지 않고 컨슈머가 처리를 시작할 수 있을 때 까지 대기 했다가 배출을 전달하며, 완료될 떄까지 이 동작을 반복한다
    Flowable.range(1, 1000)
        .map(::MyItem4) //MyItem3 객체 생성
        .observeOn(Schedulers.io())
        .subscribe({
            print("Received $it;\t")
            runBlocking { delay(50) }
        }, {
            it.printStackTrace()
        })
    runBlocking { delay(60000) }
    //플로어블을 보니 옵저버블보다 더 좋아보이지만, 항상 그런것은 아니다 -> 플로어블이 백프레셔를 제공하지만, 옵저버블이 존재하는 이유가 있다.
    /**
     * 플로어블은 옵저버블보다 느리다
     * 플로어블과 백프레셔는 더 많은 양의 데이터를 처리할 때 도움이 된다(원천에서 10000개 이상의 아이템을 배출한다면 플로어블 사용을 고려하자) -> 특히 원천이 비동기적으로 동작해 필요시 컨슈머 체인이 생산자에게 배출량을 제한/규제을 요청할 수 있는 경우 적합)
     * 파일이나 데이터베이스를 읽거나 파싱하는 경우
     * 결과를 반환하는 동안 IO 리소스의 양을 조절할 수 있는 블로킹을 지원하는 네트워크 IO 작업/스트리밍 API에서 배출할 때 사용한다.
     */
    /**
     * 옵저버블이 선호되는 조건
     * 소량의 데이터를 다룰 때
     * 오로지 동기 방식으로 작업하길 원하거나 제한된 동시성을 가진 작업을 수행할 때
     * UI 이벤트를 발생시킬때
     */

}

data class MyItem4(val id: Int){
    init {
        println("MyItem Created $id; \t")
    }
}