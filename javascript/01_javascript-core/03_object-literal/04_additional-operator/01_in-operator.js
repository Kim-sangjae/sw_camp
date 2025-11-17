// in 연산자 : 프로퍼티 존재 여부 확인
var student = {
    name : '유관순',
    age : 16,
    test : undefined
};

console.log(student.name === undefined);
// 프로퍼티는 존재하지만 프로퍼티 값이 undefined인 모호한 상황
console.log(student.test === undefined);

console.log("name" in student);
console.log("test" in student);     // 프로퍼티로서 존재함을 확인
console.log("hello" in student);