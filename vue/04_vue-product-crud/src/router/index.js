import { createRouter, createWebHistory } from 'vue-router';

import HomeView from '../views/HomeView.vue';
import ProductListView from '../views/products/ProductListView.vue';
import ProductDetailView from '../views/products/ProductDetailView.vue';
import ProductCreateView from '../views/products/ProductCreateView.vue';
import ProductEditView from '../views/products/ProductEditView.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/products',
    name: 'product-list',
    component: ProductListView
  },
  {
    path: '/products/new',
    name: 'product-create',
    component: ProductCreateView
  },
  {
    path: '/products/:productCode',
    name: 'product-detail',
    component: ProductDetailView,
    props: true
  },
  {
    path: '/products/:productCode/edit',
    name: 'product-edit',
    component: ProductEditView,
    props: true
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
