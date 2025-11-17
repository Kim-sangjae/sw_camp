// 정적 프로퍼티
class Animal {
    static planet = '지구';

    constructor(name, weight) {
        this.name = name;
        this.weight = weight;
    }
    eat(foodWeight) {
        this.weight += foodWeight;
        console.log(`${this.name}은 ${foodWeight}kg의 식사를 하고 ${this.weight}kg이 되었습니다`);
    }
}
class Human extends Animal {
    develop(language) {
        console.log(`${this.name}은 ${language}로 개발을 합니다.`)
    }
}

console.log(Animal.planet);
console.log(Human.planet);

Animal.staticProperty = 'static을 사용한 선언은 기술적으로 클래스 자체에 직접 할당하는 것과 같다.';
console.log(Animal.staticProperty);
console.log(Human.staticProperty);