package ch01

fun main() {
    var list:List<Any> = listOf("One", 2, "Three", "Four", 4.5, "Five", 6.0f) //1
    val iterator = list.iterator()//2. iterator 생성
    while (iterator.hasNext()){// 3. 데이터를 당겨온다(pull) //데이터가 수신돼 준비될때까지 현재 스레드는 블로킹된 상태에서 리스틀로부터 데이터를 당겨온다.
        println(iterator.next())//4. 해당 값 출력
    }
    //ReactiveX  프레임워크의 주요 구성은 옵저버블로 observables 클래스는 iterator 패턴의 반대에 위치한다.
    //observable 클래스에는 컨슈머가 소비할 수 있는 값을 생성하는 기본 컬렉션, 계산이 있다.
    //이터레이터와의 차이는 컨슈머가 이터레이터 패턴과 같이 프로듀서로부터 이런 값을 당겨오지 않는다는 점이다(프로듀서는 컨슈머에게 값을 알림으로 푸시한다)
}