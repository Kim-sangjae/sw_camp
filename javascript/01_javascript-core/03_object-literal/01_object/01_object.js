// 객체 리터럴 생성
var student = {
    // 프로퍼티 : 객체의 상태를 나타내는 값으로 키-값 쌍으로 구성
    name : '유관순',
    age : 16,
    // 메소드 : 프로퍼티를 참조하고 조작할 수 있는 동작
    getInfo : function() {
        return `${this.name}(은)는 ${this.age}세 입니다.`;
    }
};

console.log(student);
console.log(typeof student);
console.log(student.getInfo());

// 프로퍼티를 정의하지 않는 빈 객체 생성
var student2 = {};
console.log(student2);
console.log(typeof student2);