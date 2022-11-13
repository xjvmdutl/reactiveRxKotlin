package ch04

import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.toFlowable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

//프로세서는 플로어블의 Subjects에 해당한다.
//모든 Subjects 유형은 백프레셔를 지원하는 프로세서 타입이 있다.
fun main() {
    val flowable = listOf("String 1", "String 2", "String 3", "String 4", "String 5").toFlowable() //플로어블 생성
    val processor = PublishProcessor.create<String>()//프로세서 인스턴스를 생성
    processor. //프로세서 인스턴스 구독
        subscribe{
            println("Subscription 1: $it")
            runBlocking { delay(1000) }
            println("Subscription 1 delay")
        }
    processor.subscribe { println("Subscription 2 $it") } //프로세서 인스턴스 구독
    flowable.subscribe(processor) //프로세서 인스턴스로 플로어블에 가입
    //프로세서는 구독자가 모두 완료될 떄까지 다음 푸시를 대기하고 있다
}