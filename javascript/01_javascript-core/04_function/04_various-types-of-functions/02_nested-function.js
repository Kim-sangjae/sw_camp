// 중첩 함수
// 함수 내부에 정의 된 함수를 중첩함수 또는 내부 함수라고 한다.
// 중첩 함수를 포함하는 함수를 외부 함수라고 한다.
// 일반적으로 중첩 함수는 자신을 포함하는 외부 함수를 돕는 "헬퍼 함수"의 역할을 한다.
function outer() {
    var outerVal = '외부 함수 변수';
    function inner () {
        var innerVal = '내부 함수 변수';
        // 외부 함수의 변수를 내부 함수에서는 참조할 수 있다.
        console.log(`outerVal : ${outerVal}, innerVal : ${innerVal}`);
    }
    inner();
    // 내부 함수의 변수를 외부 함수에서는 참조할 수 없다.
    // console.log(`outerVal : ${outerVal}, innerVal : ${innerVal}`);
}
outer();

