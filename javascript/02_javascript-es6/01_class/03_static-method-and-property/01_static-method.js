// 정적 메서드
// : 클래스 '전체'에 필요한 기능을 만들 때 사용
class Student {
    constructor(name, height) {
        this.name = name;
        this.height = height;
    }
    // 클래스 선언부 안에 위치하고 static 키워드를 붙이면 정적 메서드가 선언된다.
    static compare(studentA, studentB) {
        return studentA.height - studentB.height;
    }
}

const students = [
    new Student('유관순', 165.5),
    new Student('홍길동', 180.5),
    new Student('선덕여왕', 159.5),
];

students.sort(Student.compare);
console.log(students);

Student.staticMethod = function() {
    console.log('static method는 메서드를 프로퍼티 형태로 직접 할당하는 것과 같다.')
}
Student.staticMethod();



