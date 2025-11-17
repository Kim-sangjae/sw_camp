// 논리 연산자의 단축 평가
// 표현식을 평가하는 도중 평가 결과가 확정 된 경우 나머지 평가 과정을 생략한다
// 이 때 표현식의 결과가 때로는 boolean 값이 아닐 수 도 있다

// 'apple' -> Truthy -> true 로 평가되어 논리 연산의 결과를 결정한 첫 번째 피연산자가 그대로 반환
console.log('apple' || 'banana');
console.log(false || 'banana');
// 'apple' -> Truthy -> true 로 평가되어도 &&는 우항까지 확인해야 하므로 논리 연산의 결과를 결정한
// 두 번째 피연산자가 반환
console.log('apple' && 'banana');
console.log(false && 'banana');

// 객체를 가리키기 기대하는 변수가 null 또는 undefined 가 아닌지 확인하고
// 프로퍼티를 참조할 때 단축 평가를 이용했다.
var obj = null;
// TypeError: Cannot read properties of null (reading 'value')
// var val = obj.value;
// obj가 falsy한 값이면 좌항만 실행하여 val2에는 null이 대입 되고
// obj가 truthy한 값이면 우항까지 실행하여 val2에는 obj.value가 대입
var val2 = obj && obj.value;






