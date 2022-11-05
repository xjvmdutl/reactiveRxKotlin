package ch02

import java.util.Random

fun main() {
    //익명함수이다.
    val sum = { x: Int, y: Int -> x + y }  // 두개의 숫자를 더하고 결과의 합을 반환하는 람다식
    println("Sum ${sum(12, 14)}") // 함수 호출 후 결과 출력
    val anonymousMult =
        { x: Int -> (Random().nextInt(15) + 1) * x } //15까지 제한된 난수를 전달된 x와 곱한 후 결과를 반환하는 람다 선언
    println("random output ${anonymousMult(2)}")// 결과를 다시 출력
}