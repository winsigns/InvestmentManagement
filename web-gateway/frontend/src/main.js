/**
 * author:fulin.wang at 2017-03-08
 * app main
 */
import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import VueRouter from 'vue-router'
import store from './vuex/store'
import Vuex from 'vuex'
import NProgress from 'nprogress' //页面顶部进度条
import 'nprogress/nprogress.css'
//css
import myStyle from "./assets/css/style.css"
//js
import routerConfig from './router.config.js'

Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vuex)

const routes = routerConfig;
const router = new VueRouter({
    routes
})

router.beforeEach((to, from, next) => {
    NProgress.start();
    next()
})

router.afterEach(transition => {
    NProgress.done();
});

new Vue({
    el: '#app',
    template: '<App/>',
    router,
    store,
    components: { App }
}).$mount('#app')
