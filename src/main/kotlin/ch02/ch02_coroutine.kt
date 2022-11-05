package ch02

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

suspend fun longRunningTsk():Long { //함수를 일시 중지로 표시하는 suspend 키워드 사용 -> 함수를 사용하는 동안 대기
    val time = measureTimeMillis {  //블록 시작후 time 변수에 값 할당 //실행시간 반환
        println("Please wait")
        delay(2000) //2초 지연
        println("Delay Over")
    }
    return time
}

fun main() {
    /*
    runBlocking { //longRunningTsk 함수가 완료될 때까지 프로그램 대기상태로 만든다
        val exeTime = longRunningTsk()
        println("Execution Time is $exeTime")
    }
     */
    val scope = GlobalScope
    val time = scope.async {
        longRunningTsk()
    }
    println("Print after async")
    runBlocking { println("printing time ${time.await()}") } //await를 통해 비동기로 실행된 코드를 기다린다.
}