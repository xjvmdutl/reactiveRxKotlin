package ch02

//함수의 반환값이 인수/매개 변수에 전적으로 의존하면 순수함수라고 한다.
fun square(n: Int): Int { //이름이 있는 순수 함수
    return n * n
}

fun main() {
    println("named pure func square = ${square(3)}")
    val qube = { n: Int -> n * n * n } //익명 순수함수(람다)
    println("lambda pure func qube = ${qube(3)}")
}