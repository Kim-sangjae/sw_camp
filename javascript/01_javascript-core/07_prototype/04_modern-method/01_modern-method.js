// __proto__ 를 getter, setter로 사용할 경우 키가 "__proto__" 일 때 에러가 발생할 수 있다.
const user = {
    activate : true
};
const obj = Object.create(user);    //Object.create(proto)
let key = "__proto__";
console.log(obj[key]);
obj[key] = { test : "새로운 객체 덮어쓰기" };
console.log(obj[key]);
console.log(obj.__proto__);

// 프로토타입 접근 시 사용하는 메소드
const student = Object.create(user);
console.log(Object.getPrototypeOf(student));
console.log(Object.getPrototypeOf(student) === user);
Object.setPrototypeOf(student, {});
console.log(Object.getPrototypeOf(student));
console.log(Object.getPrototypeOf(student) === user);