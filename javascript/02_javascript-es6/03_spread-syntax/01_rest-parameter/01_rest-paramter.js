// 나머지 매개변수 (...)
function mergeAll(...args) {
    let msg = '';
    for(let arg of args) msg += arg;
    return msg;
}
console.log(mergeAll('안녕하세요'));
console.log(mergeAll('안녕하세요', '반갑습니다'));
console.log(mergeAll('안녕하세요', '반갑습니다', '저는 홍길동입니다'));
