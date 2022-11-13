package ch04

import io.reactivex.Flowable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

//컨슈머가 소비할 떄까지 배출을 버퍼링하는 onBackPressureBuffer와 달리 buffer() 연산자는 배출을 모아서 리스트나 다른 컬렉션 유형으로 전달
fun main() {
    /*
    val flowable = Flowable.range(1, 111) //1~111까지 정수를 배출
    flowable.buffer(10) //10개의 데이터를 모아 리스트로 전달
        .subscribe{ println(it) }
     */
    /**
     * 두번째 매개 변수에 정수로 건너 뛰는 값을 지정해서 사용하는데 skip 매개변수 값이 count 매개변수와 일치하면 아무것도 수행하지 않는다.
     * 그렇지 않으면 count 및 skip 매개변수 사이의 양수 차이를 actual_numbers_to_skip으로 계산한 다음,
     * 1) skip 매개변수의 값이 count 매개변수의 값보다 크면 각 배출의 마지막 아이템 이후에 actual_numbers_to_skip 크기만큼의 아이템을 건너띈다
     * 2) 그렇지 않고 count 매개변수 값이 skip 매개변수의 값보다 큰 경우 롤링 버퍼가 발생하는데 아이템을 건너는 대신 이전 배출량에서 건너띈다
     */
    /*
    val flowable = Flowable.range(1, 111)
    flowable.buffer(10, 15) //구독장 5개 항목을 건너띈다(actual_numbers_to_skip = (15 - 10) = 5)
        .subscribe{ println("Subscription 1 $it") }
    flowable.buffer(15, 7) //각 배출의 8번쨰 항목부터(15-7)부터 반복 시작한다
        .subscribe{ println("Subscription 2 $it") }
     */
    //buffer 연산자는 시간 기반으로 버퍼링을 할 수 있도록 도와주는 메서드로 생각하자
    //원천으로 부터 아이템을 모아서 일정 시간 간격으로 배출할 수 있다.
    /*
    val flowable = Flowable.interval(100, TimeUnit.MILLISECONDS) //플로어블 인스턴스 생성
    flowable.buffer(1, TimeUnit.SECONDS) //모든 배출을 잠시동안 버퍼링
        .subscribe{println(it)} //Flowable.interval 이 100 밀리초마다 하나씩 배출하고 버퍼가 두번째 시간 프레임 내에서 배출을 수집한다
    runBlocking { delay(5000) }
     */
    //다른 생산자를 경계로 취할 수 있다.
    //즉 버퍼 연산자는 인접해 있는 생산자의 사이에서 모든 배출물을 모으고 각 생산자의 리스트로 배출한다.
    val boundaryFlowable = Flowable.interval(350, TimeUnit.MILLISECONDS)

    val flowable = Flowable.interval(100, TimeUnit.MILLISECONDS)
    flowable.buffer(boundaryFlowable)
        .subscribe{ println(it) }
    runBlocking { delay(5000) }
}