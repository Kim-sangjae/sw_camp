// 일반 함수와 생성자 함수의 차이
function Student(name, age) {
    console.log(this);
    this.name = name;
    this.age = age;
    this.getInfo = function() {
        return `${this.name}(은)는 ${this.age}세 입니다.`;
    }
}
// 일반 함수와 생성자 함수의 형식적인 차이는 없다. 첫 문자를 대문자로 기술해서 구별하려 노력한다.
// 단, new 연산자와 함께 호출 할 때 생성자 함수로 동작하며 new 연산자가 없이 호출되면 일반 함수로 동작한다.
const student = Student('강감찬', 35);
console.log(student);
// 일반 함수로 호출 된 Student 내의 this는 전역 객체를 가리킨다. 따라서 전역에서 참조 가능해진다.
console.log(name);
console.log(age);
console.log(getInfo());