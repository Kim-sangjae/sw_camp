// 비교 연산자
// 동등 비교 (==, !=) : 암묵적 타입 변환을 통해 타입 일치 시킨 뒤 같은 값인지 비교
// 일치 비교 (===, !==) : 타입과 값이 모두 일치하는지 비교

// 숫자 1, 문자 '1', 논리값 true 비교
console.log(`1 == '1' : ${ 1 == '1' }`);
console.log(`1 == true : ${ 1 == true }`);
console.log(`1 === '1' : ${ 1 === '1' }`);
console.log(`1 === true : ${ 1 === true }`);

// 숫자 0, 문자 '0', 논리값 false, 빈문자열 '' 비교
console.log(`0 == '0' : ${ 0 == '0' }`);
console.log(`0 == false : ${ 0 == false }`);
console.log(`0 == '' : ${ 0 == '' }`);
console.log(`0 === '0' : ${ 0 === '0' }`);
console.log(`0 === false : ${ 0 === false }`);
console.log(`0 === '' : ${ 0 === '' }`);

// null과 undefined의 비교
console.log(`null == undefined : ${ null == undefined }`);
console.log(`null === undefined : ${ null === undefined }`);

// NaN(Not a Number)은 자신과 일치하지 않는 유일한 값이다.
console.log(`NaN == NaN : ${ NaN == NaN }`);
console.log(`NaN === NaN : ${ NaN === NaN }`);
console.log(`isNaN(NaN) : ${ isNaN(NaN) }`);

// 비교연산자를 통해 문자열끼리의 비교가 가능하며 유니코드 순으로 비교한다.
console.log(`'hello' === 'hello' : ${'hello' === 'hello'}`);
console.log(`'hello' !== 'hello' : ${'hello' !== 'hello'}`);
console.log(`'apple' > 'banana' : ${'apple' > 'banana'}`);
console.log(`'apple' < 'banana' : ${'apple' < 'banana'}`);