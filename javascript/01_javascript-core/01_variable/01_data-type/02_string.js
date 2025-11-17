/* 문자 타입 */
var string;
string = 'JavaScript';
string = "JavaScript";
string = `JavaScript`;  // 백틱

// 템플릿 리터럴 : ES6부터 도입 된 멀티라인 문자열, 표현식 삽입
var str = `안녕하세요.
반갑습니다.
제 이름은 홍길동입니다.`;
console.log(str);

var lastName = '홍';
var firstName = '길동';
console.log("제 이름은 " + lastName + firstName + "입니다.");
console.log(`제 이름은 ${lastName}${firstName}입니다.`);