import { createRouter, createWebHistory } from 'vue-router'
import HomeView from "@/views/01_router/HomeView.vue";
import PathVariableView from "@/views/01_router/PathVariableView.vue";
import QueryStringView from "@/views/01_router/QueryStringView.vue";
import NestedLayout from "@/views/02_nested-router/NestedLayout.vue";
import LoginView from "@/views/03_auth/LoginView.vue";
import MyPageView from "@/views/03_auth/MyPageView.vue";

const routes = [
    {
        path : '/',
        name : 'home',
        component : HomeView
    },
    {
        path : '/pathvariable/:id',
        name : 'path-variable',
        component: PathVariableView
    },
    {
        path : '/querystring',
        name : 'query-string',
        component: QueryStringView
    },
    {
        path : '/nested',
        component: NestedLayout,
        children : [
            {
                path : 'home',
                name : 'nested-home',
                // Lazy Loading : 해당 컴포넌트가 실제로 사용되는 시점에 로딩 하도록 한다.
                // 최초 로딩 시간을 줄여주는 효과가 있다.
                component : () => import('@/views/02_nested-router/NestedHome.vue')
            },
            {
                path : 'other',
                name : 'nested-other',
                component : () => import('@/views/02_nested-router/NestedOtherView.vue')
            }
        ]
    },
    {
        path : '/login',
        name : 'login',
        component: LoginView,
        meta : {
            guestOnly : true
        }
    },
    {
        path : '/mypage',
        name : 'mypage',
        component: MyPageView,
        meta : {
            requiresAuth : true
        }
    }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
    // RouterLink 마다 active-class를 지정하지 않고 전역에서 지정
    // url과 동일한 RouterLink는 active 상태로 다른 CSS 표현
    linkActiveClass : 'active'
})

// 추후에는 Pinia Store 등으로 관리하는 것이 적합하나 여기에서는 localStorage에서 존재여부 확인
function isAuthenticated() {
    return !!localStorage.getItem('ACCESS_TOKEN');
}

router.beforeEach((to, from) => {
    // 로그인 필요한 페이지에 로그인 되지 않은 상태로 접근
    if(to.meta.requiresAuth && !isAuthenticated()) {
        // 원래 가려고 했던 위치를 query.redirect에 담아 로그인 후 다시 보낼 수 있다
        return {
            name : 'login',
            query : { redirect: to.fullPath }
        }
    }
    // 비회원만 접근해야 하는 페이지에 로그인 한 상태로 접근
    if(to.meta.guestOnly && isAuthenticated()) {
        return {
            name : 'mypage'
        }
    }
    
    // 그 외에는 그대로 라우팅 진행
    return true;
    
})




export default router
