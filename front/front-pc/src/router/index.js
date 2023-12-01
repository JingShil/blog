import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/layout/Index.vue'
import Login from '../views/login/Index.vue'
import Test from '../views/test/Index.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
    redirect: '/blog',
    children:[
      {
        path: '/blog',
        name: 'blog',
        component: () => import('../views/blog/Index.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/test',
    name: 'test',
    component: Test
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
