// 반환문

function hello(name) {
    return `${name}님 안녕하세요~`;
    // 반환문 이후의 코드는 실행되지 않고 무시 된다
    console.log(name);
}

console.log(hello('유관순'));

function func() {
    console.log('함수가 호출 되었습니다');
    // 반환 값이 없는 경우 return 구문 생략 가능하며 암묵적으로 undefined를 반환
    return; // 반환 값이 명시적으로 작성되지 않은 경우도 undefined를 반환
}

console.log(func());