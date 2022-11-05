package ch01

import io.reactivex.subjects.PublishSubject

/*
fun main() {
    var number = 4
    var isEven = isEven(number)
    println("The number is " + (if (isEven) "Even" else "Odd"))
    number = 9
    println("The number is " + (if (isEven) "Even" else "Odd")) //number가 Odd가 되었지만 isEven이 여전히 참
}

fun isEven(n: Int): Boolean = ((n % 2) == 0)

 */
fun main() {
    val subject = PublishSubject.create<Int>()
    subject.map({ isEven(it) }).subscribe({
        println("The number is " + (if (it) "Even" else "Odd"))
    })
    subject.onNext(4)
    subject.onNext(9)
}

fun isEven(n: Int): Boolean = ((n % 2) == 0)
