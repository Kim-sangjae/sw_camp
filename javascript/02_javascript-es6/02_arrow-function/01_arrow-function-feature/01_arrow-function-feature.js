// 화살표 함수의 특징
// : 기존의 함수보다 표현이 간략한 것 뿐만 아니라 내부 동작 또한 간략화 되어 있음
//   화살표 함수는 콜백함수로 자주 사용 되며 자신의 this/arguments를 갖지 않고
//   상위 스코프를 그대로 참조하도록 설계 되어 이전에 자주 발생하던 this 바인딩 문제를 해결했다.

// 1. 화살표 함수는 this를 갖지 않는다.
const theater = {
    store : '건대점',
    titles : ['어벤져스', '겨울왕국', '스파이더맨'],
    showMovieList() {
        // 화살표 함수
        this.titles.forEach(title => console.log(`${this.store} : ${title}`));
        // 일반 함수 (this는 global 객체)
        this.titles.forEach(function(title) { console.log(`${this.store} : ${title}`); });
    }
};
theater.showMovieList();

// 2. 화살표 함수는 new와 함께 호출 할 수 없다.
const func = function(){};
const arrowFunc = () => {};
// TypeError: arrowFunc is not a constructor
// new arrowFunc();
console.log(arrowFunc.hasOwnProperty("prototype"));
new func();
console.log(func.hasOwnProperty("prototype"));

// 3. 화살표 함수는 super를 가지지 않는다.
class Animal {
    constructor(name, weight) {
        this.name = name;
        this.weight = weight;
    }
    move(lostWeight){
        if(this.weight > lostWeight) {
            this.weight -= lostWeight;
            console.log(`${this.name}은 움직임으로 인해 
            ${lostWeight}kg 감량되어 ${this.weight}kg 가 되었습니다.`)
        }
    }
}
class Tiger extends Animal {
    move(lostWeight){
        // 화살표 함수
        setTimeout(() => super.move(lostWeight), 3000);
        // 일반 함수
        // setTimeout(function() { super.move(lostWeight) }, 3000);
    }
}
const tiger = new Tiger("백두산 호랑이", 90);
tiger.move(1);

// 4. 화살표 함수는 arguments를 가지지 않는다.
(function(){
    console.log(arguments);
    // 화살표 함수는 본인의 arguments가 없기 때문에 3, 4가 아닌 상위 스코프의 arguments 1, 2를 참조
    const arrowFunc = () => console.log(arguments);
    arrowFunc(3, 4);
})(1, 2);






