import { defineConfig } from 'vitest/config';
import vue from '@vitejs/plugin-vue';
import path from 'node:path';

export default defineConfig({
    plugins: [vue()],
    resolve: {
        alias: {
            // 소스 코드에서 사용하던 @ 경로를 테스트에서도 동일하게 사용하기 위함
            '@': path.resolve(__dirname, './src')
        }
    },
    test: {
        // 브라우저 DOM이 필요하므로 jsdom 사용
        environment: 'jsdom',
        // describe/it/expect 등을 전역으로 사용할 수 있게 함
        globals: true,
        // 테스트 공통 설정 파일
        setupFiles: ['./tests/setupTests.js'],
        // 테스트 파일 패턴
        include: ['tests/**/*.test.js']
    }
});
