import { delay } from './fakeClient';

const COURSE_DB = [
  {
    id: 1,
    title: 'Vue.js 입문',
    instructor: '홍길동',
    level: 'beginner',
    category: 'frontend',
    duration: 12
  },
  {
    id: 2,
    title: 'Java 백엔드 기초',
    instructor: '유관순',
    level: 'beginner',
    category: 'backend',
    duration: 20
  },
  {
    id: 3,
    title: 'Spring Boot로 REST API 만들기',
    instructor: '선덕여왕',
    level: 'intermediate',
    category: 'backend',
    duration: 24
  },
  {
    id: 4,
    title: '프론트엔드 상태 관리 심화',
    instructor: '권율',
    level: 'advanced',
    category: 'frontend',
    duration: 16
  }
];

const fetchCourses = async () => {
  return delay(COURSE_DB, 500);
};

export { fetchCourses };
