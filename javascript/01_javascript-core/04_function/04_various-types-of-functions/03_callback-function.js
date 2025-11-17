// 콜백 함수
// 함수의 매개변수를 통해 다른 함수의 내부로 전달 되는 함수를 "콜백 함수"라고 한다.
// 매개변수를 통해 외부에서 콜백 함수를 전달 받는 함수를 "고차 함수"라고 한다.
function increase(value) {
    return value + 1;
}
function decrease(value) {
    return value - 1;
}
// 전달 받은 함수에 전달 받은 값을 적용 시켜주는 고차 함수
function apply(func, value) {
    // 고차함수는 매개변수로 전달 받은 콜백 함수의 호출 시점을 결정해서 호출 한다.
    return func(value);
}
console.log(apply(increase, 5));
console.log(apply(decrease, 5));

// 콜백 함수가 고차 함수 내부에서만 호출 된다면 익명 함수 리터럴로 전달하는 것이 일반적이다.
console.log(apply(function(value){ return value + 1; }, 10));
console.log(apply(value => value - 1, 10));

// 함수의 변하지 않는 공통로직(sort)을 미리 정의해두고 경우에 따라 변경 되는 로직(compare)은
// 추상화해서 함수의 외부에서 내부로 전달하는 방식이다.
// 비동기 처리(이벤트, 타이머, ...) 에서 활용 되는 중요 패턴이며 배열 고차 함수에서도 활용 된다.
console.log([3, 1, 4, 2, 5].sort((a, b) => b - a));