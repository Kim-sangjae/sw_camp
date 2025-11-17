// 화살표 함수 기본 문법
// ES6에서 도입 된 화살표 함수는 function 키워드 대신 화살표를 사용해
// 보다 함수를 간략하게 선언하는 방법이다.
// 항상 익명함수로 선언하여 본문이 한 줄인 함수를 작성할 때 유용하다.

// 기본 함수 정의
var message = function () {
    return `hello world`;
}
console.log(message());

// function 키워드 생략
message = () => {
    return `Arrow Function`;
}
console.log(message());

// 명령문이 하나인 경우 중괄호 생략 가능
// 함수 몸체 내부의 문이 값으로 평가 될 수 있는 표현식문이라면 암묵적으로 반환 (return 생략 가능)
message = () => `Arrow Function2`;
console.log(message());

// 매개변수 있는 경우
message = (val1, val2) => 'Arrow ' + val1 + val2;
console.log(message('Function', 'Good'));

// 매개변수가 1개 있는 경우 - 소괄호 생략 가능
message = val1 => 'Arrow ' + val1;
console.log(message('Function'));